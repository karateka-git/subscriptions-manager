package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.data.local.SubscriptionsLocalDataSource
import com.vlatrof.subscriptionsmanager.data.local.room.dao.SubscriptionsDao
import com.vlatrof.subscriptionsmanager.data.local.room.database.SubscriptionsRoomDatabase
import com.vlatrof.subscriptionsmanager.data.repositories.SubscriptionsRepositoryImpl
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideSubscriptionsRepository(
        subscriptionsLocalDataSource: SubscriptionsLocalDataSource
    ): SubscriptionsRepository {
        return SubscriptionsRepositoryImpl(
            subscriptionsLocalDataSource = subscriptionsLocalDataSource
        )
    }

    @Provides
    fun provideSubscriptionsLocalDataSource(
        subscriptionsDao: SubscriptionsDao
    ): SubscriptionsLocalDataSource {
        return SubscriptionsLocalDataSource(
            subscriptionsDao = subscriptionsDao
        )
    }

    @Provides
    fun provideSubscriptionsDao(
        application: App
    ): SubscriptionsDao {
        return SubscriptionsRoomDatabase.getDatabase(application).getSubscriptionsDao()
    }
}
