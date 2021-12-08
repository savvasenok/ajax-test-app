package xyz.savvamirzoyan.share.ajaxtest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xyz.savvamirzoyan.share.ajaxtest.data.db.dao.ContactsDao

@Database(
    entities = [
        ContactDb::class
    ],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactsDb(): ContactsDao

    companion object {
        private const val DB_NAME = "main"

        @Volatile
        private var instance: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var localInstance = instance

                if (localInstance == null) {
                    localInstance = createDatabase(context)
                }

                return localInstance
            }
        }

        operator fun invoke(context: Context? = null) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context!!).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }
}