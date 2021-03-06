package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactUi
import xyz.savvamirzoyan.share.ajaxtest.ui.contactslist.ContactDomainToUiMapper

sealed class ContactsDomain : Abstract.DomainObject, ListItemGetter<ContactDomain> {

    abstract fun map(mapper: ContactDomainToUiMapper): List<ContactUi>


    data class Success(
        private val contacts: List<ContactDomain>
    ) : ContactsDomain() {
        override fun map(mapper: ContactDomainToUiMapper): List<ContactUi> =
            contacts.map { contactDomain -> contactDomain.map(mapper) }

        override fun get(position: Int) = contacts[position]
    }

    data class Fail(private val errorType: ErrorType) : ContactsDomain() {
        override fun map(mapper: ContactDomainToUiMapper): List<ContactUi> = listOf(mapper.map(errorType))

        // TODO: Could be done better
        override fun get(position: Int): ContactDomain =
            throw RuntimeException("ContactsDomain.Fail has no ContactDomain items to get")
    }
}

interface ListItemGetter<T> {
    fun get(position: Int): T
}
