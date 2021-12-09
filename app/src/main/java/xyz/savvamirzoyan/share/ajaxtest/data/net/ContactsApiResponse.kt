package xyz.savvamirzoyan.share.ajaxtest.data.net

import kotlinx.serialization.Serializable
import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

// TODO: It has to be possible to do in 1 class
@Serializable
data class ContactCloudResponse(
    private val results: List<ContactCloudUserResponse>
) : Abstract.CloudObject<List<ContactCloud>, ContactsCloudResponseToCloudMapper> {
    override fun map(mapper: ContactsCloudResponseToCloudMapper): List<ContactCloud> {
        return results.map { user ->
            mapper.map(user.name.first, user.name.last, user.email, user.picture.large)
        }
    }
}

@Serializable
data class ContactCloudUserResponse(
    val name: ContactCloudUserNameResponse,
    val email: String,
    val picture: ContactCloudUserPictureResponse
)

@Serializable
data class ContactCloudUserNameResponse(
    val first: String,
    val last: String
)

@Serializable
data class ContactCloudUserPictureResponse(
    val large: String,
    val medium: String,
    val thumbnail: String
)