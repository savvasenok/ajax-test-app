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

        private val contactsDb = roomProvider.provide().contactsDb()

        override suspend fun read(): List<ContactDb> = withContext(Dispatchers.IO) {
            contactsDb.fetchContacts()
        }

        override suspend fun read(userId: Int): ContactDb = withContext(Dispatchers.IO) {
            contactsDb.fetchContact(userId)
        }

        override suspend fun save(data: List<ContactData>) = withContext(Dispatchers.IO) {
            val contactsDbObjects = data.map { contactData ->
                contactData.map(contactDataToDbMapper)
            }

            contactsDb.saveContacts(contactsDbObjects)
        }

        override suspend fun delete(userId: Int) = withContext(Dispatchers.IO) {
            contactsDb.delete(userId)
        }

        override suspend fun delete(contact: ContactData) {
            contactsDb.delete(contact.map(contactDataToDbMapper))
        }

        override suspend fun deleteAll() = withContext(Dispatchers.IO) {
            contactsDb.deleteAll()
        }

        override suspend fun update(value: ContactData) = withContext(Dispatchers.IO) {
            val contactDb = value.map(contactDataToDbMapper)
            contactsDb.update(contactDb)
        }
    }
}