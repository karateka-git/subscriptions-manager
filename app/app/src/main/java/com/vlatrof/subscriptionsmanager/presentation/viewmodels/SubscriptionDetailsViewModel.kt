package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.*
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetSubscriptionByIdUseCase
import kotlinx.coroutines.*

class SubscriptionDetailsViewModel(

    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
//    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,

    ) : ViewModel() {

    val subscriptionLiveData = MutableLiveData<Subscription>()

    fun loadSubscriptionById(id: Int) {

        CoroutineScope(mainDispatcher).launch(mainDispatcher) {
            subscriptionLiveData.value = asyncGetSubscriptionById(id).await()
        }

    }

    private fun asyncGetSubscriptionById(id: Int): Deferred<Subscription> {

        return CoroutineScope(mainDispatcher).async(ioDispatcher) {
            return@async getSubscriptionByIdUseCase(id)
        }

    }


}