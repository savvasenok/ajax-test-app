package xyz.savvamirzoyan.share.ajaxtest.data.net

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.data.ContactCloudToDataMapper
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData

/**
 * {"results": [{"name": {"first": String,"last": String},"email": String,"picture": {"large": String,"medium": String,"thumbnail": String},}],}
 * */
@Serializable
data class ContactCloud(
    @SerialName("first") private val name: String,
    @SerialName("last") private val surname: String,
    @SerialName("email") private val email: String,
    @SerialName("large") private val photoUrl: String // TODO: Add thumbnail and proper photo
) : Abstract.CloudObject<ContactData, ContactCloudToDataMapper> {
    override fun map(mapper: ContactCloudToDataMapper): ContactData =
        mapper.map(name, surname, email, photoUrl)
}
