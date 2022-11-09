package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptiondetails

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.subscriptionsmanager.domain.usecases.deletebyid.DeleteSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getbyid.GetSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.update.UpdateSubscriptionUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SubscriptionDetailsViewModelFactory(

    private val resources: Resources,
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase,
    private val deleteSubscriptionByIdUseCase: DeleteSubscriptionByIdUseCase,
    private val mainDispatcher: CoroutineDispatcher,
    private val ioDispatcher: CoroutineDispatcher

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SubscriptionDetailsViewModel(
            resources = resources,
            getSubscriptionByIdUseCase = getSubscriptionByIdUseCase,
            updateSubscriptionUseCase = updateSubscriptionUseCase,
            deleteSubscriptionByIdUseCase = deleteSubscriptionByIdUseCase,
            mainDispatcher = mainDispatcher,
            ioDispatcher = ioDispatcher
        ) as T
    }
}
