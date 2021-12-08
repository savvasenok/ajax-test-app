package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

interface ContactDbToDataMapper : Abstract.Mapper {
    fun map(id: Int, name: String, surname: String, email: String, photoUrl: String): ContactData

    class Base : ContactDbToDataMapper {
        override fun map(
            id: Int,
            name: String,
            surname: String,
            email: String,
            photoUrl: String
        ) = ContactData(id, name, surname, email, photoUrl)
    }
}