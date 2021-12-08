package xyz.savvamirzoyan.share.ajaxtest.data.net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.savvamirzoyan.share.ajaxtest.core.Read

interface ContactsCloudDataSource : Read<List<ContactCloud>> {

    class Base(
        private val service: ContactsService,
        private val mapper: ContactsCloudResponseToCloudMapper
    ) : ContactsCloudDataSource {
        override suspend fun read(): List<ContactCloud> = withContext(Dispatchers.IO) {
            service.fetchContacts().map(mapper)
        }
    }
}