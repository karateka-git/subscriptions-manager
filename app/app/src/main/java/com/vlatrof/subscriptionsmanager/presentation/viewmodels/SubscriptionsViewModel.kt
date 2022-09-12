package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.deleteallsubscriptions.DeleteAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getallsubscriptions.GetAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnewsubscription.InsertNewSubscriptionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriptionsViewModel(

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,
    private val insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase,
    private val deleteAllSubscriptionsUseCase: DeleteAllSubscriptionsUseCase

) : ViewModel() {

    val subscriptionsLiveData: LiveData<List<Subscription>> =
        getAllSubscriptionsUseCase().asLiveData()

    fun insertNewSubscription(subscription: Subscription) {
        viewModelScope.launch(ioDispatcher) {
            insertNewSubscriptionUseCase(subscription)
        }
    }

    fun deleteAllSubscriptions() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllSubscriptionsUseCase()
        }
    }

}