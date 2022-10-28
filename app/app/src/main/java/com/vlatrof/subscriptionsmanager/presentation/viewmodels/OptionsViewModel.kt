package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.AndroidViewModel
import com.vlatrof.subscriptionsmanager.app.App

class OptionsViewModel(private val application: App) : AndroidViewModel(application) {

    fun applyNightMode(nightMode: Int) {
        application.saveNightMode(nightMode)
        application.applyNightMode(nightMode)
    }

    fun getCurrentNightMode(): Int {
        return application.getCurrentNightMode()
    }

}
