package xyz.savvamirzoyan.share.ajaxtest.ui.userdetails

import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDetailsUi

interface ContactDomainToDetailsUiMapper {

    fun map(name: String, surname: String, email: String, photoUrl: String): ContactDetailsUi

    class Base : ContactDomainToDetailsUiMapper {
        override fun map(name: String, surname: String, email: String, photoUrl: String): ContactDetailsUi =
            ContactDetailsUi.Base(name, surname, email, photoUrl)
    }
}
