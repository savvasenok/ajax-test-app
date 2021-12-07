package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDataToDomainMapper
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDomain

data class ContactData(
    private val id: Int,
    private val name: String,
    private val surname: String,
    private val email: String,
    private val photoUrl: String
) : Abstract.Object<ContactDomain, ContactDataToDomainMapper> {
    override fun map(mapper: ContactDataToDomainMapper): ContactDomain =
        mapper.map(id, name, surname, email, photoUrl)
}
