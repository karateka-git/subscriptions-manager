package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptionslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.subscriptionsmanager.domain.usecases.getallflow.GetAllSubscriptionsFlowUseCase

class SubscriptionsViewModelFactory(

    private val getAllSubscriptionsFlowUseCase: GetAllSubscriptionsFlowUseCase

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SubscriptionsViewModel(
            getAllSubscriptionsFlowUseCase = getAllSubscriptionsFlowUseCase
        ) as T
    }
}
