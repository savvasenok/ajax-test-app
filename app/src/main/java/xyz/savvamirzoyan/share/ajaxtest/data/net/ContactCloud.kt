package xyz.savvamirzoyan.share.ajaxtest.data.net

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.data.ContactCloudToDataMapper
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData


data class ContactCloud(
    private val name: String,
    private val surname: String,
    private val email: String,
    private val photoUrl: String,
    private val thumbnailUrl: String
) : Abstract.CloudObject<ContactData, ContactCloudToDataMapper> {
    override fun map(mapper: ContactCloudToDataMapper): ContactData =
        mapper.map(name, surname, email, photoUrl, thumbnailUrl)
}
