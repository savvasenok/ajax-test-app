package xyz.savvamirzoyan.share.ajaxtest.ui

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.domain.ErrorType

interface ContactDomainToUiMapper : Abstract.Mapper {

    fun map(id: Int, name: String, surname: String, photoUrl: String): ContactUi
    fun map(errorType: ErrorType): ContactUi

    class Base(
        private val resourceProvider: ResourceProvider
    ) : ContactDomainToUiMapper {
        override fun map(id: Int, name: String, surname: String, photoUrl: String) =
            ContactUi.Base(id, "$name $surname", photoUrl)

        override fun map(errorType: ErrorType) = ContactUi.Fail(errorType, resourceProvider)
    }
}