package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.data.local.SubscriptionsLocalDataSource
import com.vlatrof.subscriptionsmanager.data.local.room.dao.SubscriptionsDao
import com.vlatrof.subscriptionsmanager.data.local.room.database.SubscriptionsRoomDatabase
import com.vlatrof.subscriptionsmanager.data.repositories.SubscriptionsRepositoryImpl
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single<SubscriptionsRepository> {
        SubscriptionsRepositoryImpl(subscriptionsLocalDataSource = get())
    }

    single<SubscriptionsLocalDataSource> {
        SubscriptionsLocalDataSource(subscriptionsDao = get())
    }

    single<SubscriptionsDao> {
        SubscriptionsRoomDatabase.getDatabase(androidApplication()).getSubscriptionsDao()
    }
}
