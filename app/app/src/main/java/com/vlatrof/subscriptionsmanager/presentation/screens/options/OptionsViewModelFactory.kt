package com.vlatrof.subscriptionsmanager.presentation.screens.options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.subscriptionsmanager.app.App
import kotlinx.coroutines.CoroutineDispatcher

class OptionsViewModelFactory(

    private val application: App,
    private val ioDispatcher: CoroutineDispatcher

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OptionsViewModel(
            application = application,
            ioDispatcher = ioDispatcher
        ) as T
    }
}
