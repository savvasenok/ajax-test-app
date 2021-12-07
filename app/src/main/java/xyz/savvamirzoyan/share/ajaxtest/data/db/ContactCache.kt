package xyz.savvamirzoyan.share.ajaxtest.data.db

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.data.ContactCacheToDataMapper
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData

// TODO: Make it as Room entity
data class ContactCache(
    private val name: String,
    private val surname: String,
    private val email: String,
    private val photoUrl: String,
    private val id: Int
) : Abstract.Object<ContactData, ContactCacheToDataMapper> {
    override fun map(mapper: ContactCacheToDataMapper): ContactData =
        mapper.map(id, name, surname, email, photoUrl)
}
