package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactsDbDataSource
import xyz.savvamirzoyan.share.ajaxtest.data.net.ContactsCloudDataSource
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDomain
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDomainToDataMapper

interface ContactsRepository {

    suspend fun read(): ContactsData
    suspend fun read(contactId: Int): ContactData
    suspend fun delete(contactId: Int)
    suspend fun delete(contact: ContactDomain)
    suspend fun deleteAll()
    suspend fun update(value: ContactDomain)

    class Base(
        private val cloudSource: ContactsCloudDataSource,
        private val dbSource: ContactsDbDataSource,
        private val contactDbToDataMapper: ContactDbToDataMapper,
        private val contactCloudToDataMapper: ContactCloudToDataMapper,
        private val contactDomainToDataMapper: ContactDomainToDataMapper
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

        override suspend fun read(contactId: Int): ContactData =
            dbSource.read(contactId).map(contactDbToDataMapper)

        override suspend fun delete(contactId: Int) {
            dbSource.delete(contactId)
        }

        override suspend fun delete(contact: ContactDomain) {
            dbSource.delete(contact.map(contactDomainToDataMapper))
        }

        override suspend fun deleteAll() {
            dbSource.deleteAll()
        }

        override suspend fun update(value: ContactDomain) {
            val contactData = value
                .map(contactDomainToDataMapper)

            dbSource.update(contactData)
        }
    }
}