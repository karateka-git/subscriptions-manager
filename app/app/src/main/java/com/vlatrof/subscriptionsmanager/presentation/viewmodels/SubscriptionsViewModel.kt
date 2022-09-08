package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.GetAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.InsertNewSubscriptionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriptionsViewModel(

    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,
    private val insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase

) : ViewModel() {

    private val _subscriptionsMutableLiveData =
        getAllSubscriptionsUseCase.execute().asLiveData()
    val subscriptionsLiveData: LiveData<List<Subscription>> = _subscriptionsMutableLiveData

    fun insertNewSubscription(subscription: Subscription) {
        viewModelScope.launch(Dispatchers.IO) {
            insertNewSubscriptionUseCase.execute(subscription)
        }
    }

}