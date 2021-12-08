package xyz.savvamirzoyan.share.ajaxtest.data.db

import android.content.Context

interface RoomProvider {

    fun provide(): AppDatabase

    class Base(private val context: Context) : RoomProvider {
        override fun provide() = AppDatabase.getInstance(context)
    }
}