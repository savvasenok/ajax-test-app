package xyz.savvamirzoyan.share.ajaxtest.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData

interface ContactsDbDataSource {

    suspend fun read(): List<ContactDb>
    suspend fun read(userId: Int): ContactDb
    suspend fun save(data: List<ContactData>)

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
    }
}