package xyz.savvamirzoyan.share.ajaxtest.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.savvamirzoyan.share.ajaxtest.core.Abstract
import xyz.savvamirzoyan.share.ajaxtest.data.ContactData
import xyz.savvamirzoyan.share.ajaxtest.data.ContactDbToDataMapper

@Entity(tableName = "contacts")
data class ContactDb(
    val name: String,
    val surname: String,
    val email: String,
    val photoUrl: String,
    val thumbnailUrl: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Abstract.DbObject<ContactData, ContactDbToDataMapper> {
    override fun map(mapper: ContactDbToDataMapper): ContactData =
        mapper.map(id, name, surname, email, photoUrl, thumbnailUrl)
}
