package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptionslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetAllSubscriptionsUseCase

class SubscriptionsViewModel(

    getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,

    ) : ViewModel() {

    val subscriptionsLiveData: LiveData<List<Subscription>> =
        getAllSubscriptionsUseCase().asLiveData()

}