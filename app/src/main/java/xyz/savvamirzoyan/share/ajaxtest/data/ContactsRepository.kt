package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.core.Read
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactsDbDataSource
import xyz.savvamirzoyan.share.ajaxtest.data.net.ContactsCloudDataSource

interface ContactsRepository : Read<List<ContactData>> {

    class Base(
        private val cloudSource: ContactsCloudDataSource,
        private val dbSource: ContactsDbDataSource,
        private val contactDbToDataMapper: ContactDbToDataMapper,
        private val contactCloudToDataMapper: ContactCloudToDataMapper
    ) : ContactsRepository {
        override suspend fun read(): List<ContactData> {
            val cache = dbSource.read().map { it.map(contactDbToDataMapper) }
            if (cache.isEmpty()) {
                return cloudSource.read().map { it.map(contactCloudToDataMapper) }
                // TODO: Save data from api
            }

            return cache
        }
    }
}