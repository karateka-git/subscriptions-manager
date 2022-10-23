package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.vlatrof.subscriptionsmanager.app.App

class OptionsViewModel(application: App) : AndroidViewModel(application) {

    private val sharedPreferences =
        getApplication<Application>()
            .getSharedPreferences(App.APP_PREFERENCES, Context.MODE_PRIVATE)

    fun applyNightMode(nightMode: Int) {
        getApplication<App>().let { app ->
            app.saveNightMode(nightMode)
            app.applyNightMode(nightMode)
        }
    }

    fun getCurrentNightMode(): Int {
        return sharedPreferences.getInt(App.NIGHT_MODE, -1)
    }

}
