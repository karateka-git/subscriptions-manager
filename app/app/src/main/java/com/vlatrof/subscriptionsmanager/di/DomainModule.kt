package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.domain.usecases.GetAllSubscriptionsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetAllSubscriptionsUseCase> {
        GetAllSubscriptionsUseCase()
    }

}