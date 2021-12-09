package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDomainToUiMapper
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactUi

sealed class ContactsDomain : Abstract.DomainObject {

    abstract fun map(mapper: ContactDomainToUiMapper): List<ContactUi>

    data class Success(
        private val contacts: List<ContactDomain>
    ) : ContactsDomain() {
        override fun map(mapper: ContactDomainToUiMapper): List<ContactUi> =
            contacts.map { contactDomain -> contactDomain.map(mapper) }

    }

    data class Fail(private val errorType: ErrorType) : ContactsDomain() {
        override fun map(mapper: ContactDomainToUiMapper): List<ContactUi> = listOf(mapper.map(errorType))
    }
}
