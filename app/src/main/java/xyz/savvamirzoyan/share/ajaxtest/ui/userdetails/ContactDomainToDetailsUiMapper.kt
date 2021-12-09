package xyz.savvamirzoyan.share.ajaxtest.ui.userdetails

import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDetailsUi

interface ContactDomainToDetailsUiMapper {

    fun map(name: String, surname: String, email: String, photoUrl: String, thumbnailUrl: String): ContactDetailsUi

    class Base : ContactDomainToDetailsUiMapper {
        override fun map(
            name: String,
            surname: String,
            email: String,
            photoUrl: String,
            thumbnailUrl: String
        ): ContactDetailsUi =
            ContactDetailsUi.Base(name, surname, email, photoUrl, thumbnailUrl)
    }
}
