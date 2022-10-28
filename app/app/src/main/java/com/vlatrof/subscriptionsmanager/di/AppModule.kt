package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.NewSubscriptionViewModel
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.OptionsViewModel
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionDetailsViewModel
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionsViewModel
import org.koin.android.ext.koin.androidContext
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
            application = androidContext() as App,
        )
    }

    viewModel<SubscriptionDetailsViewModel> {
        SubscriptionDetailsViewModel(
            getSubscriptionByIdUseCase = get(),
            updateSubscriptionUseCase = get(),
            deleteSubscriptionByIdUseCase = get(),
        )
    }

    viewModel<OptionsViewModel> {
        OptionsViewModel(
            application = androidContext() as App
        )
    }

}