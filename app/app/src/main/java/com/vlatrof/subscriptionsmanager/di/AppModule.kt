package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<SubscriptionsViewModel> {
        SubscriptionsViewModel(
            getAllSubscriptionsUseCase = get(),
            insertNewSubscriptionUseCase = get(),
            deleteAllSubscriptionsUseCase = get()
        )
    }

}