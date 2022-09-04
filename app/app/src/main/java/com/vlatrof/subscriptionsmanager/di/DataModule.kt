package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.data.repositories.SubscriptionRepositoryImpl
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionRepository
import org.koin.dsl.module

val dataModule = module {

    single<SubscriptionRepository> {
        SubscriptionRepositoryImpl()
    }

}