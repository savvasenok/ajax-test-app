package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDataToDomainMapper
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDomain

sealed class ContactData : Abstract.Object<ContactDomain, ContactDataToDomainMapper> {

    data class Success(
        private val id: Int,
        private val name: String,
        private val surname: String,
        private val email: String,
        private val photoUrl: String
    ) : ContactData() {
        override fun map(mapper: ContactDataToDomainMapper): ContactDomain =
            mapper.map(id, name, surname, email, photoUrl)
    }


    data class Fail(private val e: Exception) : ContactData() {
        override fun map(mapper: ContactDataToDomainMapper): ContactDomain {
            return mapper.map(e)
        }
    }
}
