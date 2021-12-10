package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.core.Matcher
import xyz.savvamirzoyan.share.ajaxtest.core.Updater
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactDataToDbMapper
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDetailsUi
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactUi
import xyz.savvamirzoyan.share.ajaxtest.ui.contactslist.ContactDomainToUiMapper
import xyz.savvamirzoyan.share.ajaxtest.ui.userdetails.ContactDomainToDetailsUiMapper

data class ContactDomain(
    private val id: Int,
    private val name: String,
    private val surname: String,
    private val email: String,
    private val photoUrl: String,
    private val thumbnailUrl: String
) : Abstract.DomainObject, ContactDomainMatcher {
    fun map(mapper: ContactDomainToUiMapper): ContactUi = mapper.map(id, name, surname, thumbnailUrl)
    fun map(mapper: ContactDomainToDetailsUiMapper): ContactDetailsUi =
        mapper.map(name, surname, email, photoUrl, thumbnailUrl)

    fun map(mapper: ContactDomainToDataMapper): ContactData =
        mapper.map(id, name, surname, email, photoUrl, thumbnailUrl)

    fun map(mapper: ContactDataToDbMapper) = mapper.map(id, name, surname, email, photoUrl, thumbnailUrl)

    fun update(updater: ContactDomainUpdate): ContactDomain =
        updater.update(id, photoUrl, thumbnailUrl)

    override fun matchName(text: String): Boolean = name == text

    override fun matchSurname(text: String): Boolean = surname == text

    override fun matchEmail(text: String): Boolean = email == text

}

interface ContactDomainMatcher : Matcher {
    fun matchName(text: String): Boolean
    fun matchSurname(text: String): Boolean
    fun matchEmail(text: String): Boolean
}

interface ContactDomainUpdate : Updater {
    fun update(
        id: Int,
        photoUrl: String,
        thumbnailUrl: String
    ): ContactDomain
}
