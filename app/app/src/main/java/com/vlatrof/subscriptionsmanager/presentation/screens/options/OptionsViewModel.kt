package com.vlatrof.subscriptionsmanager.presentation.screens.options

import android.net.Uri
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.data.local.room.database.SubscriptionsRoomDatabase
import com.vlatrof.subscriptionsmanager.data.local.room.entities.SubscriptionEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.util.Scanner

class OptionsViewModel(

    private val application: App,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : AndroidViewModel(application) {

    fun applyNightMode(nightMode: Int) {
        application.saveNightMode(nightMode)
        application.applyNightMode(nightMode)
    }

    fun getCurrentNightMode(): Int {
        return application.getCurrentNightMode()
    }

    fun exportSubscriptions(directoryUri: Uri?) {
        if (directoryUri == null) {
            // TODO: message from resources
            Toast.makeText(application, "false", Toast.LENGTH_SHORT).show()
            return
        }
        exportOnBackground(directoryUri)
    }

    fun importSubscriptions(contentUri: Uri?) {
        if (contentUri == null) {
            // TODO: message from resources
            Toast.makeText(application, "false", Toast.LENGTH_SHORT).show()
            return
        }
        importObBackground(contentUri)
    }
    
    private fun exportOnBackground(directoryUri: Uri) = viewModelScope.launch(ioDispatcher) {
        // get subscriptions from database
        val subscriptions =
            SubscriptionsRoomDatabase.getDatabase(application).getSubscriptionsDao().getAll()

        // create file to save subscriptions
        val fileToSave = DocumentFile.fromTreeUri(application, directoryUri)!!
            .createFile("application/json", "subscriptions_export.json")

        // write subscriptions to file
        try {
            application.contentResolver.openOutputStream(fileToSave!!.uri).use { outputStream ->
                val jsonData = Json.encodeToString(subscriptions)
                outputStream!!.write(jsonData.toByteArray(charset = Charsets.UTF_8))
            }
            // TODO: message from resources
            Toast.makeText(application, "true", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            // TODO: message from resources
            Toast.makeText(application, "false", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun importObBackground(contentUri: Uri) = viewModelScope.launch(ioDispatcher) {
        try {
            application.contentResolver.openInputStream(contentUri).use { inputStream ->
                val scanner = Scanner(inputStream).useDelimiter("\\A")
                val jsonStr = if (scanner.hasNext()) scanner.next() else ""
                val importedSubscriptions =
                    Json.decodeFromString<List<SubscriptionEntity>>(jsonStr!!)
                val subscriptionsDao =
                    SubscriptionsRoomDatabase.getDatabase(application).getSubscriptionsDao()
                importedSubscriptions.forEach { subscriptionsDao.insert(it) }
                // TODO: message from resources
                Toast.makeText(application, "true", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            // TODO: message from resources
            Toast.makeText(application, "false", Toast.LENGTH_SHORT).show()
        }
    }
}
