package com.vlatrof.subscriptionsmanager.app

import android.app.Application
import android.content.Context
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

        // apply current night mode
        AppCompatDelegate.setDefaultNightMode(
            getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .getInt(NIGHT_MODE, MODE_NIGHT_NO)
        )

        // init Koin DI
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(
                    dataModule,
                    domainModule,
                    appModule,
                )
            )
        }

    }

    companion object {
        // shared preferences keys
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val NIGHT_MODE = "NIGHT_MODE"
    }

}