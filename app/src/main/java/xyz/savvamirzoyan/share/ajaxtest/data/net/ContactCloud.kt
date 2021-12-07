package xyz.savvamirzoyan.share.ajaxtest.data.net

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.data.ContactCloudToDataMapper
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData

// TODO: Make it compatible with Retrofit
/**
 * {"results": [{"name": {"first": String,"last": String},"email": String,"picture": {"large": String,"medium": String,"thumbnail": String},}],}
 * */
data class ContactCloud(
    private val name: String,
    private val surname: String,
    private val email: String,
    private val photoUrl: String
) : Abstract.Object<ContactData, ContactCloudToDataMapper> {
    override fun map(mapper: ContactCloudToDataMapper): ContactData =
        mapper.map(name, surname, email, photoUrl)
}
