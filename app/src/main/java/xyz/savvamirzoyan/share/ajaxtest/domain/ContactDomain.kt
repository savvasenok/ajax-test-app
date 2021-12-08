package xyz.savvamirzoyan.share.ajaxtest.domain

import retrofit2.HttpException
import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDomainToUiMapper
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactUi
import java.net.UnknownHostException

sealed class ContactDomain : Abstract.Object<ContactUi, ContactDomainToUiMapper> {

    data class Success(
        private val id: Int,
        private val name: String,
        private val surname: String,
        private val email: String,
        private val photoUrl: String
    ) : ContactDomain() {
        override fun map(mapper: ContactDomainToUiMapper): ContactUi =
            mapper.map(id, name, surname, photoUrl)
    }

    data class Fail(
        private val e: Exception,
    ) : ContactDomain() {
        override fun map(mapper: ContactDomainToUiMapper): ContactUi = mapper.map(
            when (e) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_UNAVAILABLE
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}
