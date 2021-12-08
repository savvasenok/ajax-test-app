package xyz.savvamirzoyan.share.ajaxtest.ui

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

interface ContactDomainToUiMapper : Abstract.Mapper {

    fun map(id: Int, name: String, surname: String, photoUrl: String): ContactUi
    fun map(error: String): ContactUi

    class Base : ContactDomainToUiMapper {
        override fun map(id: Int, name: String, surname: String, photoUrl: String) =
            ContactUi.Base(id, "$name $surname", photoUrl)

        override fun map(error: String) = ContactUi.Fail(error)
    }
}