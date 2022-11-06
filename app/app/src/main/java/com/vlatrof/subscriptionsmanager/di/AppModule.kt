package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.presentation.screens.newsubscription.NewSubscriptionViewModel
import com.vlatrof.subscriptionsmanager.presentation.screens.options.OptionsViewModel
import com.vlatrof.subscriptionsmanager.presentation.screens.subscriptiondetails.SubscriptionDetailsViewModel
import com.vlatrof.subscriptionsmanager.presentation.screens.subscriptionslist.SubscriptionsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<SubscriptionsViewModel> {
        SubscriptionsViewModel(
            getAllSubscriptionsFlowUseCase = get()
        )
    }

    viewModel<NewSubscriptionViewModel> {
        NewSubscriptionViewModel(
            insertNewSubscriptionUseCase = get(),
            application = androidContext() as App
        )
    }

    viewModel<SubscriptionDetailsViewModel> {
        SubscriptionDetailsViewModel(
            resources = androidContext().resources,
            getSubscriptionByIdUseCase = get(),
            updateSubscriptionUseCase = get(),
            deleteSubscriptionByIdUseCase = get()
        )
    }

    viewModel<OptionsViewModel> {
        OptionsViewModel(
            application = androidContext() as App
        )
    }
}
