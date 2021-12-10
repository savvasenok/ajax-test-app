package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.data.ContactData

interface ContactDomainToDataMapper {

    fun map(id: Int, name: String, surname: String, email: String, photoUrl: String, thumbnailUrl: String): ContactData

    class Base : ContactDomainToDataMapper {
        override fun map(
            id: Int,
            name: String,
            surname: String,
            email: String,
            photoUrl: String,
            thumbnailUrl: String
        ) = ContactData(id, name, surname, email, photoUrl, thumbnailUrl)
    }
}