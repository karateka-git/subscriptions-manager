package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.DeleteAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetAllSubscriptionsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class SubscriptionsViewModel(

    getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,

    ) : ViewModel() {

    val subscriptionsLiveData: LiveData<List<Subscription>> =
        getAllSubscriptionsUseCase().asLiveData()

}