package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactsDbDataSource
import xyz.savvamirzoyan.share.ajaxtest.data.net.ContactsCloudDataSource

interface ContactsRepository {

    suspend fun read(): ContactsData
    suspend fun read(userId: Int): ContactData
    suspend fun delete(userId: Int)

    class Base(
        private val cloudSource: ContactsCloudDataSource,
        private val dbSource: ContactsDbDataSource,
        private val contactDbToDataMapper: ContactDbToDataMapper,
        private val contactCloudToDataMapper: ContactCloudToDataMapper
    ) : ContactsRepository {
        override suspend fun read(): ContactsData = try {
            var cache = dbSource.read().map { it.map(contactDbToDataMapper) }

            if (cache.isEmpty()) {
                val result = cloudSource.read().map { it.map(contactCloudToDataMapper) }
                dbSource.save(result)
                cache = dbSource.read().map { it.map(contactDbToDataMapper) }
            }

            ContactsData.Success(cache)

        } catch (e: Exception) {
            ContactsData.Fail(e)
        }

        override suspend fun read(userId: Int): ContactData =
            dbSource.read(userId).map(contactDbToDataMapper)

        override suspend fun delete(userId: Int) {
            dbSource.delete(userId)
        }
    }
}