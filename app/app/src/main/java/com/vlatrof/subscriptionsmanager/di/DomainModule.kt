package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.DeleteAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.implementations.DeleteAllSubscriptionsUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.implementations.GetAllSubscriptionsUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.implementations.GetSubscriptionByIdUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.InsertNewSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.implementations.InsertNewSubscriptionUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetSubscriptionByIdUseCase
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

}