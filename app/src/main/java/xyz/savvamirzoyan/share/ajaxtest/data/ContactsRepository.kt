package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.core.Read
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactsDbDataSource
import xyz.savvamirzoyan.share.ajaxtest.data.net.ContactsCloudDataSource

interface ContactsRepository : Read<ContactsData> {

    class Base(
        private val cloudSource: ContactsCloudDataSource,
        private val dbSource: ContactsDbDataSource,
        private val contactDbToDataMapper: ContactDbToDataMapper,
        private val contactCloudToDataMapper: ContactCloudToDataMapper
    ) : ContactsRepository {
        override suspend fun read(): ContactsData = try {
            val cache = dbSource.read()
            if (cache.isEmpty()) {
                val result = cloudSource.read().map { it.map(contactCloudToDataMapper) }
                dbSource.save(result)
                // TODO: we have to save and then read again to get objects with proper ids in db
                ContactsData.Success(result)
            } else {
                ContactsData.Success(cache.map { it.map(contactDbToDataMapper) })
            }
        } catch (e: Exception) {
            ContactsData.Fail(e)
        }
    }
}