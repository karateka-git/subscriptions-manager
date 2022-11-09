package com.vlatrof.subscriptionsmanager.presentation.screens.newsubscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnew.InsertNewSubscriptionUseCase
import kotlinx.coroutines.CoroutineDispatcher

class NewSubscriptionViewModelFactory(

    private val application: App,
    private val ioDispatcher: CoroutineDispatcher,
    private val insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewSubscriptionViewModel(
            application = application,
            ioDispatcher = ioDispatcher,
            insertNewSubscriptionUseCase = insertNewSubscriptionUseCase
        ) as T
    }
}
