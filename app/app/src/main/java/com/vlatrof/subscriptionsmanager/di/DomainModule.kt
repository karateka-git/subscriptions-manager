package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.domain.usecases.implementations.*
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.*
import org.koin.dsl.module

val domainModule = module {

    factory<GetAllSubscriptionsUseCase> {
        GetAllSubscriptionsUseCaseImpl(subscriptionsRepository = get())
    }

    factory<InsertNewSubscriptionUseCase> {
        InsertNewSubscriptionUseCaseImpl(subscriptionsRepository = get())
    }

    factory<DeleteAllSubscriptionsUseCase> {
        DeleteAllSubscriptionsUseCaseImpl(subscriptionsRepository = get())
    }

    factory<GetSubscriptionByIdUseCase> {
        GetSubscriptionByIdUseCaseImpl(subscriptionsRepository = get())
    }

    factory<DeleteSubscriptionByIdUseCase> {
        DeleteSubscriptionByIdUseCaseImpl(subscriptionsRepository = get())
    }

    factory<UpdateSubscriptionUseCase> {
        UpdateSubscriptionUseCaseImpl(subscriptionsRepository = get())
    }

}