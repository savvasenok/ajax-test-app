package xyz.savvamirzoyan.share.ajaxtest.ui

import xyz.savvamirzoyan.share.ajaxtest.R
import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.domain.ErrorType

sealed class ContactUi : Abstract.Object<Unit, ContactUi.ContactUiMapper> {

    override fun map(mapper: ContactUiMapper) {}

    object Progress : ContactUi()

    data class Base(
        private val id: Int,
        private val fullName: String,
        private val photoUrl: String
    ) : ContactUi() {
        override fun map(mapper: ContactUiMapper) {
            mapper.map(id, fullName, photoUrl)
        }
    }

    data class Fail(
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider
    ) : ContactUi() {
        override fun map(mapper: ContactUiMapper) {
            val messageId = when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.error_no_connection
                ErrorType.SERVICE_UNAVAILABLE -> R.string.error_service_unavailable
                ErrorType.GENERIC_ERROR -> R.string.something_went_wrong
            }

            val message = resourceProvider.getString(messageId)
            mapper.map(message)
        }
    }

    interface ContactUiMapper : Abstract.Mapper {
        fun map(id: Int, name: String, photoUrl: String)
        fun map(error: String)
    }
}
