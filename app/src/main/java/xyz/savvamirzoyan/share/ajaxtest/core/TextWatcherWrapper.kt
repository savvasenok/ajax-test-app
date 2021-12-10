package xyz.savvamirzoyan.share.ajaxtest.core

import android.text.Editable
import android.text.TextWatcher

interface TextWatcherWrapper : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(s?.toString() ?: "")
    }

    fun onTextChanged(text: String)
}