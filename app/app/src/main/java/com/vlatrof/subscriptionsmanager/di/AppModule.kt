package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.presentation.viewmodels.NewSubscriptionViewModel
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionDetailsViewModel
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<SubscriptionsViewModel> {
        SubscriptionsViewModel(
            getAllSubscriptionsUseCase = get(),
        )
    }

    viewModel<NewSubscriptionViewModel> {
        NewSubscriptionViewModel(
            insertNewSubscriptionUseCase = get(),
        )
    }

    viewModel<SubscriptionDetailsViewModel> {
        SubscriptionDetailsViewModel(
            getSubscriptionByIdUseCase = get(),
            deleteSubscriptionByIdUseCase = get()
        )
    }

}