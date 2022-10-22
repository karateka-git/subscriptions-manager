package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.vlatrof.subscriptionsmanager.app.App

class OptionsViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences =
        getApplication<Application>()
            .getSharedPreferences(App.APP_PREFERENCES, Context.MODE_PRIVATE)

    var alertNightModeWasShown = false

    fun saveNewNightMode(newNightModeValue: Int) {
        sharedPreferences.edit().putInt(App.NIGHT_MODE, newNightModeValue).apply()
    }

    fun getCurrentNightMode(): Int {
        return sharedPreferences.getInt(App.NIGHT_MODE, -1)
    }

}
