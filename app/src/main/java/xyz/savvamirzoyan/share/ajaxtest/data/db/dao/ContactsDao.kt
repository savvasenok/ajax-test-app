package xyz.savvamirzoyan.share.ajaxtest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactDb

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts WHERE id = :userId")
    suspend fun fetchContact(userId: Int): ContactDb

    @Query("SELECT * FROM contacts")
    suspend fun fetchContacts(): List<ContactDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContacts(contacts: List<ContactDb>)

    @Query("DELETE FROM contacts WHERE id = :userId")
    suspend fun delete(userId: Int)
}