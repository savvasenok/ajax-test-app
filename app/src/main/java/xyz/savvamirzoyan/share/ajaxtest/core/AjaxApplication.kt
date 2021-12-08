package xyz.savvamirzoyan.share.ajaxtest.core

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import xyz.savvamirzoyan.share.ajaxtest.data.ContactCloudToDataMapper
import xyz.savvamirzoyan.share.ajaxtest.data.ContactDbToDataMapper
import xyz.savvamirzoyan.share.ajaxtest.data.ContactsRepository
import xyz.savvamirzoyan.share.ajaxtest.data.db.AppDatabase
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactDataToDbMapper
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactsDbDataSource
import xyz.savvamirzoyan.share.ajaxtest.data.db.RoomProvider
import xyz.savvamirzoyan.share.ajaxtest.data.net.ContactsCloudDataSource
import xyz.savvamirzoyan.share.ajaxtest.data.net.ContactsCloudResponseToCloudMapper
import xyz.savvamirzoyan.share.ajaxtest.data.net.ContactsService
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDataToDomainMapper
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactsDataToDomainMapper
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactsInteractor
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDomainToUiMapper
import xyz.savvamirzoyan.share.ajaxtest.ui.ResourceProvider
import xyz.savvamirzoyan.share.ajaxtest.ui.contactslist.ContactsListViewModel

class AjaxApplication : Application() {

    private companion object {
        private const val BASE_URL = "https://randomuser.me/api/"
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    lateinit var contactsListViewModel: ContactsListViewModel

    @ExperimentalSerializationApi
    override fun onCreate() {
        super.onCreate()

        AppDatabase.getInstance(this)

        // Retrofit and OkHttp3
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
        val contactsService = retrofit.create(ContactsService::class.java)

        // Resource Provider
        val resourceProvider = ResourceProvider.Base(this)

        // Mappers
        val contactDbToDataMapper = ContactDbToDataMapper.Base()
        val contactCloudToDataMapper = ContactCloudToDataMapper.Base()
        val contactDataToDomainMapper = ContactDataToDomainMapper.Base()
        val contactsDataToDomainMapper = ContactsDataToDomainMapper.Base(contactDataToDomainMapper)
        val contactDomainToUiMapper = ContactDomainToUiMapper.Base(resourceProvider)
        val contactsCloudResponseToCloudMapper = ContactsCloudResponseToCloudMapper.Base()
        val contactDataToDbMapper = ContactDataToDbMapper.Base()

        // Sources and Repositories
        val contactsDbDataSource = ContactsDbDataSource.Base(
            RoomProvider.Base(this),
            contactDataToDbMapper
        )
        val contactsCloudDataSource =
            ContactsCloudDataSource.Base(contactsService, contactsCloudResponseToCloudMapper)
        val contactsRepository = ContactsRepository.Base(
            contactsCloudDataSource,
            contactsDbDataSource,
            contactDbToDataMapper,
            contactCloudToDataMapper
        )

        // ViewModels
        contactsListViewModel = ContactsListViewModel(
            ContactsInteractor.Base(contactsRepository, contactsDataToDomainMapper),
            contactDomainToUiMapper
        )
    }
}