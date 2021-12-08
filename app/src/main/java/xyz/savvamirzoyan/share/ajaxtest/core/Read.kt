package xyz.savvamirzoyan.share.ajaxtest.core

interface Read<R> {
    suspend fun read(): R
}