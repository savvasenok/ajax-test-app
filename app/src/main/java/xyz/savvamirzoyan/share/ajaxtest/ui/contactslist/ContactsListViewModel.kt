package xyz.savvamirzoyan.share.ajaxtest.ui.contactslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactsInteractor
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDomainToUiMapper
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactUi

class ContactsListViewModel(
    private val contactsInteractor: ContactsInteractor,
    private val contactsDomainToUiMapper: ContactDomainToUiMapper
) : ViewModel() {

    private val _contactsUiFlow = MutableStateFlow<List<ContactUi>>(listOf(ContactUi.Progress))
    val contactsUiFlow: StateFlow<List<ContactUi>> = _contactsUiFlow

    fun fetchContacts() {
        viewModelScope.launch {
            val result = contactsInteractor
                .fetchContacts()
                .map { contactDomain ->
                    contactDomain.map(contactsDomainToUiMapper)
                }

            _contactsUiFlow.emit(result)
        }
    }
}
