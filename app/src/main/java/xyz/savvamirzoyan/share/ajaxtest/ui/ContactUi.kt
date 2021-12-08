package xyz.savvamirzoyan.share.ajaxtest.ui

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

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
        private val errorMessage: String
    ) : ContactUi() {
        override fun map(mapper: ContactUiMapper) {
            mapper.map(errorMessage)
        }
    }

    interface ContactUiMapper : Abstract.Mapper {
        fun map(id: Int, name: String, photoUrl: String)
        fun map(error: String)
    }
}
