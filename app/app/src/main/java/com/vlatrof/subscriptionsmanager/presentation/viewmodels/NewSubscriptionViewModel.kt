package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnewsubscription.InsertNewSubscriptionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewSubscriptionViewModel (

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase

    ) : ViewModel() {

    var currentSelectionDate: Long = 0

    fun insertNewSubscription(subscription: Subscription) {
        viewModelScope.launch(ioDispatcher) {
            insertNewSubscriptionUseCase(subscription)
        }
    }

}