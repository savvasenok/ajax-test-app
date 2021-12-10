package xyz.savvamirzoyan.share.ajaxtest.ui.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.share.ajaxtest.R
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDetailsInteractor
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDetailsUi
import xyz.savvamirzoyan.share.ajaxtest.ui.ResourceProvider

class ContactDetailsViewModel(
    private val contactDetailsInteractor: ContactDetailsInteractor,
    private val contactDomainToUiMapper: ContactDomainToDetailsUiMapper,
    resourceProvider: ResourceProvider
) : ViewModel() {

    private val nameOrSurnameIsEmptyError = resourceProvider.getString(R.string.value_is_empty)
    private val emailIsInvalidError = resourceProvider.getString(R.string.email_is_invalid)

    private val _contactDetailsUiFlow = MutableStateFlow<ContactDetailsUi>(ContactDetailsUi.Progress)
    val contactDetailsUiFlow: StateFlow<ContactDetailsUi> = _contactDetailsUiFlow

    private val _returnBackFlow = Channel<Unit>()
    val returnBackFlow: Flow<Unit> = _returnBackFlow.receiveAsFlow()

    private val _nameErrorFlow = Channel<String?>()
    val nameErrorFlow: Flow<String?> = _nameErrorFlow.receiveAsFlow()
    private val _surnameErrorFlow = Channel<String?>()
    val surnameErrorFlow: Flow<String?> = _surnameErrorFlow.receiveAsFlow()
    private val _emailErrorFlow = Channel<String?>()
    val emailErrorFlow: Flow<String?> = _emailErrorFlow.receiveAsFlow()

    private val _saveButtonIsEnableFlow = Channel<Boolean>()
    val saveButtonIsEnableFlow: Flow<Boolean> = _saveButtonIsEnableFlow
        .receiveAsFlow()
        .onStart { emit(false) }

    fun fetchUser(userId: Int) {
        viewModelScope.launch {
            val result = contactDetailsInteractor
                .fetchUser(userId)
                .map(contactDomainToUiMapper)
            _contactDetailsUiFlow.emit(result)
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            contactDetailsInteractor.deleteUser(userId)
            _returnBackFlow.send(Unit)
        }
    }

    fun saveChanges(userId: Int) {
        viewModelScope.launch {
            contactDetailsInteractor.updateUser()
            fetchUser(userId)
        }
    }

    fun updateName(newName: String) {
        contactDetailsInteractor.updateName(newName)
        viewModelScope.launch {
            if (!contactDetailsInteractor.isNameOrSurnameValid(newName)) {
                _nameErrorFlow.send(nameOrSurnameIsEmptyError)
            } else {
                _nameErrorFlow.send(null)
            }

            updateSaveButtonStatus()
        }
    }

    fun updateSurname(newSurname: String) {
        contactDetailsInteractor.updateSurname(newSurname)
        viewModelScope.launch {
            if (!contactDetailsInteractor.isNameOrSurnameValid(newSurname)) {
                _surnameErrorFlow.send(nameOrSurnameIsEmptyError)
            } else {
                _surnameErrorFlow.send(null)
            }

            updateSaveButtonStatus()
        }
    }

    fun updateEmail(newEmail: String) {
        contactDetailsInteractor.updateEmail(newEmail)
        viewModelScope.launch {
            if (!contactDetailsInteractor.isEmailValid(newEmail)) {
                _emailErrorFlow.send(emailIsInvalidError)
            } else {
                _emailErrorFlow.send(null)
            }

            updateSaveButtonStatus()
        }
    }

    private fun updateSaveButtonStatus() {
        val result = contactDetailsInteractor.isSaveButtonEnabled()
        viewModelScope.launch {
            _saveButtonIsEnableFlow.send(result)
        }
    }
}
