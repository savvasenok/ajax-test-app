package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.data.ContactsRepository

interface ContactDetailsInteractor {

    suspend fun fetchUser(userId: Int): ContactDomain
    suspend fun deleteUser(userId: Int)

    class Base(
        private val contactsRepository: ContactsRepository,
        private val contactDataToDomainMapper: ContactDataToDomainMapper
    ) : ContactDetailsInteractor {
        override suspend fun fetchUser(userId: Int) =
            contactsRepository.read(userId).map(contactDataToDomainMapper)

        override suspend fun deleteUser(userId: Int) {
            contactsRepository.delete(userId)
        }
    }
}
