package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.getallsubscriptions.GetAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnewsubscription.InsertNewSubscriptionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriptionsViewModel(

    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,
    private val insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase

) : ViewModel() {

    val subscriptionsLiveData: LiveData<List<Subscription>> =
        getAllSubscriptionsUseCase().asLiveData()

    fun insertNewSubscription(subscription: Subscription) {
        viewModelScope.launch(Dispatchers.IO) {
            insertNewSubscriptionUseCase(subscription)
        }
    }

}