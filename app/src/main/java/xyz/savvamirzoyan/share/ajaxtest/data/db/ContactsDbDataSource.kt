package xyz.savvamirzoyan.share.ajaxtest.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.savvamirzoyan.share.ajaxtest.core.Read
import xyz.savvamirzoyan.share.ajaxtest.core.Save
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData

interface ContactsDbDataSource : Read<List<ContactDb>>, Save<List<ContactData>> {

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

        override suspend fun save(data: List<ContactData>) = withContext(Dispatchers.IO) {
            val contactsDb = data.map { contactData ->
                contactData.mapToDb(contactDataToDbMapper)
            }

            roomProvider
                .provide()
                .contactsDb()
                .saveContacts(contactsDb)
        }
    }
}