package xyz.savvamirzoyan.share.ajaxtest.ui

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

sealed class ContactDetailsUi : Abstract.UiObject {

    open fun map(mapper: ContactDetailsUiMapper) = Unit

    object Progress : ContactDetailsUi()

    data class Base(
        private val name: String,
        private val surname: String,
        private val email: String,
        private val photoUrl: String,
        private val thumbnailUrl: String
    ) : ContactDetailsUi() {
        override fun map(mapper: ContactDetailsUiMapper) {
            mapper.map(name, surname, email, photoUrl, thumbnailUrl)
        }
    }

    interface ContactDetailsUiMapper : Abstract.Mapper {
        fun map(name: String, surname: String, email: String, photoUrl: String, thumbnailUrl: String)
    }
}