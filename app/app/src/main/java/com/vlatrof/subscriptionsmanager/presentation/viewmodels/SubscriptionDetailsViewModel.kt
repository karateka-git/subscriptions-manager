package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.*
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetSubscriptionByIdUseCase
import kotlinx.coroutines.*

class SubscriptionDetailsViewModel(

    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,

    ) : ViewModel() {

    val subscriptionLiveData = MutableLiveData<Subscription>()

    fun loadSubscriptionById(id: Int) {

        viewModelScope.launch(mainDispatcher) {

            delay(1000L)

            val loadedSubscription = viewModelScope.async(ioDispatcher) {
                return@async getSubscriptionByIdUseCase(id)
            }

            subscriptionLiveData.value = loadedSubscription .await()

        }

    }

//    private fun asyncGetSubscriptionById(id: Int): Deferred<Subscription> {
//
//        val jobWithResult = viewModelScope.async(ioDispatcher) {
//            return@async getSubscriptionByIdUseCase(id)
//        }
//
//        return jobWithResult
//
//    }


}