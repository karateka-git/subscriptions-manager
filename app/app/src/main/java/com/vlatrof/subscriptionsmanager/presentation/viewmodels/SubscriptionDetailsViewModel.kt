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

        // return if already downloaded
        if (subscriptionLiveData.value != null) return

        // Create and start new coroutine with Dispatchers.Main;
        // We need coroutine to use await() suspend function and receive future value from db;
        // We need Dispatchers.Main because only on main thread we have access to save data into
        // LiveData.value
        viewModelScope.launch(mainDispatcher) {

            // Create and start new coroutine with Dispatchers.IO;
            // We use Dispatchers.IO to work on background thread to load data from Room Database;
            // async() builder will create the coroutine and return its related DeferredJob;
            val deferredLoadingJob = viewModelScope.async(ioDispatcher) {
                return@async getSubscriptionByIdUseCase(id)
            }

            // we use await() on this deferred job to receive completed value in future
            subscriptionLiveData.value = deferredLoadingJob.await()

        }

    }

}