package xyz.savvamirzoyan.share.ajaxtest.data.net

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

interface ContactsCloudResponseToCloudMapper : Abstract.Mapper {

    fun map(name: String, surname: String, email: String, photoUrl: String, thumbnailUrl: String): ContactCloud

    class Base : ContactsCloudResponseToCloudMapper {
        // TODO: add thumbnail photo
        override fun map(name: String, surname: String, email: String, photoUrl: String, thumbnailUrl: String) =
            ContactCloud(name, surname, email, photoUrl, thumbnailUrl)
    }
}