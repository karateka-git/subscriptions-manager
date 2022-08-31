package com.vlatrof.subscriptionsmanager.app

import android.app.Application
import com.vlatrof.subscriptionsmanager.di.appModule
import com.vlatrof.subscriptionsmanager.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // koin configuration
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    domainModule
                )
            )
        }

    }

}