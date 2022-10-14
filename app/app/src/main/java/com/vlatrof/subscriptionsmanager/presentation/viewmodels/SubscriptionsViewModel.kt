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
import kotlinx.coroutines.launch

class SubscriptionsViewModel(

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,
    private val deleteAllSubscriptionsUseCase: DeleteAllSubscriptionsUseCase

    ) : ViewModel() {

    val subscriptionsLiveData: LiveData<List<Subscription>> =
        getAllSubscriptionsUseCase().asLiveData()

    fun deleteAllSubscriptions() {
        viewModelScope.launch(ioDispatcher) {
            deleteAllSubscriptionsUseCase()
        }
    }

}