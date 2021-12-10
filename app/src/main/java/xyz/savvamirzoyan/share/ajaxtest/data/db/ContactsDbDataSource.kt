package xyz.savvamirzoyan.share.ajaxtest.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData

interface ContactsDbDataSource {

    suspend fun read(): List<ContactDb>
    suspend fun read(userId: Int): ContactDb
    suspend fun save(data: List<ContactData>)
    suspend fun delete(userId: Int)
    suspend fun delete(contact: ContactData)
    suspend fun deleteAll()
    suspend fun update(value: ContactData)

    class Base(
        private val roomProvider: RoomProvider,
        private val contactDataToDbMapper: ContactDataToDbMapper
    ) : ContactsDbDataSource {
        override suspend fun read(): List<ContactDb> = withContext(Dispatchers.IO) {
            roomProvider
                .provide()
                .contactsDb()
                .fetchContacts()
        }

        override suspend fun read(userId: Int): ContactDb = withContext(Dispatchers.IO) {
            roomProvider
                .provide()
                .contactsDb()
                .fetchContact(userId)
        }

        override suspend fun save(data: List<ContactData>) = withContext(Dispatchers.IO) {
            val contactsDb = data.map { contactData ->
                contactData.map(contactDataToDbMapper)
            }

            roomProvider
                .provide()
                .contactsDb()
                .saveContacts(contactsDb)
        }

        override suspend fun delete(userId: Int) = withContext(Dispatchers.IO) {
            roomProvider
                .provide()
                .contactsDb()
                .delete(userId)
        }

        override suspend fun delete(contact: ContactData) {
            roomProvider
                .provide()
                .contactsDb()
                .delete(contact.map(contactDataToDbMapper))
        }

        override suspend fun deleteAll() = withContext(Dispatchers.IO) {
            roomProvider
                .provide()
                .contactsDb()
                .deleteAll()
        }

        override suspend fun update(value: ContactData) = withContext(Dispatchers.IO) {
            val contactDb = value.map(contactDataToDbMapper)

            roomProvider
                .provide()
                .contactsDb()
                .update(contactDb)
        }
    }
}