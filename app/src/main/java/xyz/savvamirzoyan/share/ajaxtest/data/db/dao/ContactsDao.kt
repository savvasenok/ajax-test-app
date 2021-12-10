package xyz.savvamirzoyan.share.ajaxtest.data.db.dao

import androidx.room.*
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactDb

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    suspend fun fetchContact(contactId: Int): ContactDb

    @Query("SELECT * FROM contacts")
    suspend fun fetchContacts(): List<ContactDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContacts(contacts: List<ContactDb>)

    @Query("DELETE FROM contacts WHERE id = :contactId")
    suspend fun delete(contactId: Int)

    @Delete
    suspend fun delete(contact: ContactDb)

    @Query("DELETE FROM contacts")
    suspend fun deleteAll()

    @Update
    suspend fun update(value: ContactDb)
}