package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

interface ContactCloudToDataMapper : Abstract.Mapper {

    fun map(name: String, surname: String, email: String, photoUrl: String, thumbnailUrl: String): ContactData

    // TODO: decide how to create unique id
    // probable provide some id to constructor from outside
    class Base : ContactCloudToDataMapper {
        override fun map(
            name: String,
            surname: String,
            email: String,
            photoUrl: String,
            thumbnailUrl: String
        ) = ContactData(
            0, name, surname, email, photoUrl, thumbnailUrl
        )
    }
}