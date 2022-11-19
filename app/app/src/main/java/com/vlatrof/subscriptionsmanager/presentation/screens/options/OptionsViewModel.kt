package com.vlatrof.subscriptionsmanager.presentation.screens.options

import android.net.Uri
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.data.local.room.database.SubscriptionsRoomDatabase
import com.vlatrof.subscriptionsmanager.data.local.room.entities.SubscriptionEntity
import java.io.FileNotFoundException
import java.io.IOException
import java.util.Scanner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OptionsViewModel(

    private val application: App,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : AndroidViewModel(application) {

    fun getCurrentNightMode() = application.getCurrentNightMode()

    fun applyNightMode(nightMode: Int) {
        application.saveNightMode(nightMode)
        application.applyNightMode(nightMode)
    }

    fun exportSubscriptions(directoryUri: Uri?) {
        if (directoryUri == null) {
            // User didn't choose any directory for export
            Toast.makeText(
                application,
                application.resources.getString(
                    R.string.options_toast_subscriptions_export_no_dir_chosen
                ),
                Toast.LENGTH_LONG
            ).show()
            return
        }
        exportOnBackground(directoryUri)
    }

    fun importSubscriptions(contentFileUri: Uri?) {
        if (contentFileUri == null) {
            // User didn't choose any file for import
            Toast.makeText(
                application,
                application.resources.getString(
                    R.string.options_toast_subscriptions_import_no_file_chosen
                ),
                Toast.LENGTH_LONG
            ).show()
            return
        }
        importOnBackground(contentFileUri)
    }

    private fun exportOnBackground(directoryUri: Uri) = viewModelScope.launch(ioDispatcher) {
        val subscriptions =
            SubscriptionsRoomDatabase.getDatabase(application).getSubscriptionsDao().getAll()
        if (subscriptions.isEmpty()) return@launch
        try {
            val fileToSave = DocumentFile.fromTreeUri(application, directoryUri)!!
                .createFile("application/json", "subscriptions_export.json")
            application.contentResolver.openOutputStream(fileToSave!!.uri).use { stream ->
                if (stream == null) return@launch
                val jsonData = Json.encodeToString(subscriptions)
                stream.write(jsonData.toByteArray(charset = Charsets.UTF_8))
            }
        } catch (exception: UnsupportedOperationException) {
            return@launch
        } catch (e: IOException) {
            return@launch
        }
    }

    private fun importOnBackground(contentFileUri: Uri) = viewModelScope.launch(ioDispatcher) {
        try {
            application.contentResolver.openInputStream(contentFileUri).use { inputStream ->
                val scanner = Scanner(inputStream).useDelimiter("\\A")
                val jsonStr = if (scanner.hasNext()) scanner.next() else ""
                if (jsonStr.isEmpty()) return@launch
                val importedSubscriptions =
                    Json.decodeFromString<List<SubscriptionEntity>>(jsonStr)
                val subscriptionsDao =
                    SubscriptionsRoomDatabase.getDatabase(application).getSubscriptionsDao()
                importedSubscriptions.forEach { subscriptionsDao.insert(it) }
            }
        } catch (exception: FileNotFoundException) {
            return@launch
        } catch (exception: SerializationException) {
            return@launch
        }
    }
}
