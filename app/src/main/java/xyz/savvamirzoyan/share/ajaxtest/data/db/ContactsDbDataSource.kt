package xyz.savvamirzoyan.share.ajaxtest.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.savvamirzoyan.share.ajaxtest.core.Read

interface ContactsDbDataSource : Read<List<ContactDb>> {

    class Base(private val roomProvider: RoomProvider) : ContactsDbDataSource {
        override suspend fun read(): List<ContactDb> = withContext(Dispatchers.IO) {
            roomProvider
                .provide()
                .contactsDb()
                .fetchContacts()
        }
    }
}