package com.vlatrof.subscriptionsmanager.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.vlatrof.subscriptionsmanager.di.appModule
import com.vlatrof.subscriptionsmanager.di.dataModule
import com.vlatrof.subscriptionsmanager.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO) // todo: for testing

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    appModule,
                )
            )
        }

    }

}