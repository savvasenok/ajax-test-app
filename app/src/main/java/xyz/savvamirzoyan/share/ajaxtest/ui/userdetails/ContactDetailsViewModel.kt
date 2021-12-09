package xyz.savvamirzoyan.share.ajaxtest.ui.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDetailsInteractor
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDetailsUi

class ContactDetailsViewModel(
    private val contactDetailsInteractor: ContactDetailsInteractor,
    private val contactDomainToUiMapper: ContactDomainToDetailsUiMapper
) : ViewModel() {

    private val _contactDetailsUiFlow = MutableStateFlow<ContactDetailsUi>(ContactDetailsUi.Progress)
    val contactDetailsUiFlow: StateFlow<ContactDetailsUi> = _contactDetailsUiFlow

    fun fetchUser(userId: Int) {
        viewModelScope.launch {
            val result = contactDetailsInteractor
                .fetchUser(userId)
                .map(contactDomainToUiMapper)
            _contactDetailsUiFlow.emit(result)
        }
    }
}
