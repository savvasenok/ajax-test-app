package xyz.savvamirzoyan.share.ajaxtest.core

import android.app.Application
import xyz.savvamirzoyan.share.ajaxtest.data.ContactCloudToDataMapper
import xyz.savvamirzoyan.share.ajaxtest.data.ContactDbToDataMapper
import xyz.savvamirzoyan.share.ajaxtest.data.db.AppDatabase
import xyz.savvamirzoyan.share.ajaxtest.data.db.ContactsDbDataSource
import xyz.savvamirzoyan.share.ajaxtest.data.db.RoomProvider
import xyz.savvamirzoyan.share.ajaxtest.domain.ContactDataToDomainMapper
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDomainToUiMapper

class AjaxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppDatabase.getInstance(this)

        // Mappers
        val contactDbToDataMapper = ContactDbToDataMapper.Base()
        val contactCloudToDataMapper = ContactCloudToDataMapper.Base()
        val contactDataToDomainMapper = ContactDataToDomainMapper.Base()
        val contactDomainToUiMapper = ContactDomainToUiMapper.Base()

        val contactsDbDataSource = ContactsDbDataSource.Base(RoomProvider.Base(this))
    }
}