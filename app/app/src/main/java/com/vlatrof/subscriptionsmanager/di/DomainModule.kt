package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.domain.usecases.GetAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.InsertNewSubscriptionUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetAllSubscriptionsUseCase> {
        GetAllSubscriptionsUseCase(subscriptionsRepository = get())
    }

    factory<InsertNewSubscriptionUseCase> {
        InsertNewSubscriptionUseCase(subscriptionsRepository = get())
    }

}