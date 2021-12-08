package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactsDataToDomainMapper
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactsDomain

sealed class ContactsData : Abstract.Object<ContactsDomain, ContactsDataToDomainMapper> {

    data class Success(private val contacts: List<ContactData>) : ContactsData() {
        override fun map(mapper: ContactsDataToDomainMapper): ContactsDomain = mapper.map(contacts)
    }

    data class Fail(private val e: Exception) : ContactsData() {
        override fun map(mapper: ContactsDataToDomainMapper): ContactsDomain = mapper.map(e)
    }
}
