package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.data.ContactsRepository

interface ContactsInteractor {

    suspend fun fetchContacts(): List<ContactDomain>

    class Base(
        private val contactsRepository: ContactsRepository,
        private val contactsDataToDomainMapper: ContactDataToDomainMapper
    ) : ContactsInteractor {
        override suspend fun fetchContacts(): List<ContactDomain> =
            contactsRepository.read().map { it.map(contactsDataToDomainMapper) }
    }
}
