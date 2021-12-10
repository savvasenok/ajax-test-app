package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.data.ContactsRepository

interface ContactsInteractor {

    suspend fun fetchContacts(): ContactsDomain
    suspend fun clearContacts()
    suspend fun deleteUserByPosition(position: Int)

    class Base(
        private val contactsRepository: ContactsRepository,
        private val contactsDataToDomainMapper: ContactsDataToDomainMapper
    ) : ContactsInteractor {

        private var cachedDomain: ContactsDomain = ContactsDomain.Success(listOf())

        override suspend fun fetchContacts(): ContactsDomain {
            val result = contactsRepository.read().map(contactsDataToDomainMapper)
            cachedDomain = result

            return result
        }

        override suspend fun clearContacts() {
            contactsRepository.deleteAll()
        }

        override suspend fun deleteUserByPosition(position: Int) {
            contactsRepository.delete(cachedDomain.get(position))
        }
    }
}
