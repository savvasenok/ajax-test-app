package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

interface ContactDataToDomainMapper : Abstract.Mapper {

    fun map(id: Int, name: String, surname: String, email: String, photoUrl: String): ContactDomain
    fun map(e: Exception): ContactDomain

    class Base : ContactDataToDomainMapper {
        override fun map(
            id: Int,
            name: String,
            surname: String,
            email: String,
            photoUrl: String
        ) = ContactDomain.Success(id, name, surname, email, photoUrl)

        override fun map(e: Exception) = ContactDomain.Fail(e)
    }
}