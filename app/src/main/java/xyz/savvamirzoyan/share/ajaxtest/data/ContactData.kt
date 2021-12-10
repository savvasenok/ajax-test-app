package xyz.savvamirzoyan.share.ajaxtest.data

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactDataToDbMapper
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactDb
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDataToDomainMapper
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDomain

data class ContactData(
    private val id: Int,
    private val name: String,
    private val surname: String,
    private val email: String,
    private val photoUrl: String,
    private val thumbnailUrl: String
) : Abstract.DataObject {

    fun map(mapper: ContactDataToDomainMapper): ContactDomain =
        mapper.map(id, name, surname, email, photoUrl, thumbnailUrl)

    fun map(mapper: ContactDataToDbMapper): ContactDb =
        mapper.map(id, name, surname, email, photoUrl, thumbnailUrl)
}