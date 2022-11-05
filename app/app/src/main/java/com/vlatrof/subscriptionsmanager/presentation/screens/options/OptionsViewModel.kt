package com.vlatrof.subscriptionsmanager.presentation.screens.options

import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.data.local.room.database.SubscriptionsRoomDatabase
import com.vlatrof.subscriptionsmanager.data.local.room.entities.SubscriptionEntity
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnew.InsertNewSubscriptionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.util.Scanner

class OptionsViewModel(

    private val application: App,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase

) : AndroidViewModel(application) {

    fun applyNightMode(nightMode: Int) {
        application.saveNightMode(nightMode)
        application.applyNightMode(nightMode)
    }

    fun getCurrentNightMode(): Int {
        return application.getCurrentNightMode()
    }

    // todo: this needs BIG refactor
    fun importSubscriptions(contentUri: Uri) {
        viewModelScope.launch(ioDispatcher) {
            application.contentResolver.openInputStream(contentUri).use { inputStream ->
                val s = Scanner(inputStream).useDelimiter("\\A")
                val jsonStr = if (s.hasNext()) s.next() else ""
                val importedSubscriptions =
                    Json.decodeFromString<List<SubscriptionEntity>>(jsonStr!!)
                val subscriptionsDao =
                    SubscriptionsRoomDatabase.getDatabase(application).getSubscriptionsDao()
                importedSubscriptions.forEach {
                    subscriptionsDao.insert(it)
                }
            }
        }
    }

    // todo: this needs BIG refactor
    fun exportSubscriptions(directoryUri: Uri) = viewModelScope.launch(ioDispatcher) {
        // get subscriptions from database
        val deferred = async() {
            SubscriptionsRoomDatabase.getDatabase(application).getSubscriptionsDao().getAll()
        }
        val subscriptions = deferred.await()

        // create file to save subscriptions
        val fileToSave = DocumentFile.fromTreeUri(application, directoryUri)!!
            .createFile("application/json", "subscriptions_export.json")

        // write subscriptions to file
        try {
            application.contentResolver.openOutputStream(fileToSave!!.uri).use { outputStream ->
                val jsonData = Json.encodeToString(subscriptions)
                outputStream!!.write(jsonData.toByteArray(charset = Charsets.UTF_8))
            }
        } catch (e: IOException) {
            // todo
            return@launch
        }
    }
}
