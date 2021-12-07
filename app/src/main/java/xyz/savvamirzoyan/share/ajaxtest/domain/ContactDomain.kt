package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDomainToUiMapper
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactUi

data class ContactDomain(
    private val id: Int,
    private val name: String,
    private val surname: String,
    private val email: String,
    private val photoUrl: String
) : Abstract.Object<ContactUi, ContactDomainToUiMapper> {
    override fun map(mapper: ContactDomainToUiMapper): ContactUi =
        mapper.map(id, name, surname, photoUrl)
}
