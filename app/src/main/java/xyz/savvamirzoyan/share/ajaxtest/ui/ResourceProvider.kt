package xyz.savvamirzoyan.share.ajaxtest.ui

import android.content.Context
import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes messageId: Int): String

    class Base(private val context: Context) : ResourceProvider {
        override fun getString(messageId: Int): String = context.getString(messageId)
    }
}
