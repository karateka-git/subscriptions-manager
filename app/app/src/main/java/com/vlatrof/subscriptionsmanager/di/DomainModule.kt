package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.domain.usecases.getallsubscriptions.GetAllSubscriptionsUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnewsubscription.InsertNewSubscriptionUseCaseImpl
import org.koin.dsl.module

val domainModule = module {

    factory<GetAllSubscriptionsUseCaseImpl> {
        GetAllSubscriptionsUseCaseImpl(subscriptionsRepository = get())
    }

    factory<InsertNewSubscriptionUseCaseImpl> {
        InsertNewSubscriptionUseCaseImpl(subscriptionsRepository = get())
    }

}