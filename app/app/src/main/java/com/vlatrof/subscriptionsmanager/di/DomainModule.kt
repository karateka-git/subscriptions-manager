package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.domain.usecases.deleteallsubscriptions.DeleteAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.deleteallsubscriptions.DeleteAllSubscriptionsUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.getallsubscriptions.GetAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getallsubscriptions.GetAllSubscriptionsUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnewsubscription.InsertNewSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnewsubscription.InsertNewSubscriptionUseCaseImpl
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

}