package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.data.ContactsRepository

interface ContactsInteractor {

    suspend fun fetchContacts(): ContactsDomain
    suspend fun clearContacts()

    class Base(
        private val contactsRepository: ContactsRepository,
        private val contactsDataToDomainMapper: ContactsDataToDomainMapper
    ) : ContactsInteractor {
        override suspend fun fetchContacts(): ContactsDomain =
            contactsRepository.read().map(contactsDataToDomainMapper)

        override suspend fun clearContacts() {
            contactsRepository.deleteAll()
        }
    }
}
