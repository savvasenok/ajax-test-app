package xyz.savvamirzoyan.share.ajaxtest.data.db

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

interface ContactDataToDbMapper : Abstract.Mapper {

    fun map(id: Int, name: String, surname: String, email: String, photoUrl: String, thumbnailUrl: String): ContactDb

    class Base : ContactDataToDbMapper {
        override fun map(
            id: Int,
            name: String,
            surname: String,
            email: String,
            photoUrl: String,
            thumbnailUrl: String
        ) = ContactDb(name, surname, email, photoUrl, thumbnailUrl, id)
    }
}
