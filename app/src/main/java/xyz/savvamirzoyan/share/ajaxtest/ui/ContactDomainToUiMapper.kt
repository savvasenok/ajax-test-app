package xyz.savvamirzoyan.share.ajaxtest.ui

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

interface ContactDomainToUiMapper : Abstract.Mapper {

    fun map(id: Int, name: String, surname: String, photoUrl: String): ContactUi

    class Base : ContactDomainToUiMapper {
        override fun map(id: Int, name: String, surname: String, photoUrl: String) =
            ContactUi(id, "$name $surname", photoUrl)
    }
}