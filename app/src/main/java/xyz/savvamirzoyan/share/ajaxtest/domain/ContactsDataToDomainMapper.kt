package xyz.savvamirzoyan.share.ajaxtest.domain

import retrofit2.HttpException
import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData
import java.net.UnknownHostException

interface ContactsDataToDomainMapper : Abstract.Mapper {

    fun map(contacts: List<ContactData>): ContactsDomain
    fun map(exception: Exception): ContactsDomain

    class Base(
        private val contactDataToDomainMapper: ContactDataToDomainMapper
    ) : ContactsDataToDomainMapper {
        override fun map(contacts: List<ContactData>) =
            ContactsDomain.Success(contacts.map { it.map(contactDataToDomainMapper) })

        override fun map(exception: Exception) = ContactsDomain.Fail(
            when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_UNAVAILABLE
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}
