package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDetailsUi
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDomainToUiMapper
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactUi
import xyz.savvamirzoyan.share.ajaxtest.ui.userdetails.ContactDomainToDetailsUiMapper

data class ContactDomain(
    private val id: Int,
    private val name: String,
    private val surname: String,
    private val email: String,
    private val photoUrl: String
) : Abstract.DomainObject {
    fun map(mapper: ContactDomainToUiMapper): ContactUi = mapper.map(id, name, surname, photoUrl)
    fun map(mapper: ContactDomainToDetailsUiMapper): ContactDetailsUi = mapper.map(name, surname, email, photoUrl)
}
