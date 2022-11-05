package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptionslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.getallflow.GetAllSubscriptionsFlowUseCase

class SubscriptionsViewModel(

    getAllSubscriptionsFlowUseCase: GetAllSubscriptionsFlowUseCase

) : ViewModel() {

    val subscriptionsLiveData: LiveData<List<Subscription>> =
        getAllSubscriptionsFlowUseCase().asLiveData()
}
