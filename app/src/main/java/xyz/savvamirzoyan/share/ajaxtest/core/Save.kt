package xyz.savvamirzoyan.share.ajaxtest.core

interface Save<T> {
    suspend fun save(data: T)
}