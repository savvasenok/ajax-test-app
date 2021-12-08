package xyz.savvamirzoyan.share.ajaxtest.data.net

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ContactsService {

    @Headers("Accept: application/json")
    @GET("1.3")
    suspend fun fetchContacts(@Query("results") amount: Int = 42): ContactCloudResponse
}