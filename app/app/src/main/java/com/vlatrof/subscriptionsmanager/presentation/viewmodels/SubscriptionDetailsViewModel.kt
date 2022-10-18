package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

        // Create and start new coroutine with Dispatchers.Main;
        // We use Dispatchers.Main so that coroutine have access to save data into
        // subscriptionLiveData.value (this can be performed only on main thread);
        viewModelScope.launch(mainDispatcher) {

            // Create and start new coroutine with Dispatchers.IO;
            // We use Dispatchers.IO to load data from Room Database on the background thread;
            // async() will create the coroutine and return its related DeferredJob
            // that can be awaited to get completed value in future
            val deferredLoadingJob = viewModelScope.async(ioDispatcher) {
                return@async getSubscriptionByIdUseCase(id)
            }

            subscriptionLiveData.value = deferredLoadingJob.await()

        }

    }


}