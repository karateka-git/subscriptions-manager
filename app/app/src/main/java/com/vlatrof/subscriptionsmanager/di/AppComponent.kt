package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.presentation.screens.newsubscription.NewSubscriptionFragment
import com.vlatrof.subscriptionsmanager.presentation.screens.options.OptionsFragment
import com.vlatrof.subscriptionsmanager.presentation.screens.subscriptiondetails.SubscriptionDetailsFragment
import com.vlatrof.subscriptionsmanager.presentation.screens.subscriptionslist.SubscriptionsFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {

    fun inject(subscriptionsFragment: SubscriptionsFragment)
    fun inject(newSubscriptionFragment: NewSubscriptionFragment)
    fun inject(subscriptionDetailsFragment: SubscriptionDetailsFragment)
    fun inject(optionsFragment: OptionsFragment)
}
