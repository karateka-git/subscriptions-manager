package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.GetAllSubscriptionsUseCase

class SubscriptionsViewModel(

    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase

) : ViewModel() {

    private val subscriptionsMutableLiveData = MutableLiveData<List<Subscription>>()

    // to prevent changing subscriptionsMutableLiveData.value from outside
    val subscriptionsLiveData: LiveData<List<Subscription>> = subscriptionsMutableLiveData

    fun getAllSubscriptions() {
        subscriptionsMutableLiveData.value = getAllSubscriptionsUseCase.execute()
    }

}