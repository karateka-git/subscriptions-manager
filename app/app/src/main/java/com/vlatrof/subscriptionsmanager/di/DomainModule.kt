package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.domain.usecases.deletebyid.DeleteSubscriptionByIdUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.getall.GetAllSubscriptionsUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.getbyid.GetSubscriptionByIdUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnew.InsertNewSubscriptionUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.update.UpdateSubscriptionUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.deletebyid.DeleteSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getall.GetAllSubscriptionsUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getbyid.GetSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnew.InsertNewSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.update.UpdateSubscriptionUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetAllSubscriptionsUseCase> {
        GetAllSubscriptionsUseCaseImpl(subscriptionsRepository = get())
    }

    factory<InsertNewSubscriptionUseCase> {
        InsertNewSubscriptionUseCaseImpl(subscriptionsRepository = get())
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
