package com.vlatrof.subscriptionsmanager.presentation.screens.options

import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.data.local.room.database.SubscriptionsRoomDatabase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Scanner

class OptionsViewModel(private val application: App) : AndroidViewModel(application) {

    fun applyNightMode(nightMode: Int) {
        application.saveNightMode(nightMode)
        application.applyNightMode(nightMode)
    }

    fun getCurrentNightMode(): Int {
        return application.getCurrentNightMode()
    }

    fun importSubscriptions(contentUri: Uri) {
        application.contentResolver.openInputStream(contentUri).use { inputStream ->
            val s = Scanner(inputStream).useDelimiter("\\A")
            val jsonStr = if (s.hasNext()) s.next() else ""
        }
    }

    fun exportSubscriptions(directoryUri: Uri) = viewModelScope.launch {
        val deferred = async {
            SubscriptionsRoomDatabase.getDatabase(application).getSubscriptionsDao().getAll()
        }
        val subscriptions = deferred.await()

        val fileToSave = DocumentFile.fromTreeUri(application, directoryUri)!!
            .createFile("application/json", "subscriptions_export.json")

        application.contentResolver.openOutputStream(fileToSave!!.uri).use { outputStream ->

        }
    }
}
