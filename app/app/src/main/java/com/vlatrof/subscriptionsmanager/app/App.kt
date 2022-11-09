package com.vlatrof.subscriptionsmanager.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import com.vlatrof.subscriptionsmanager.di.AppComponent
import com.vlatrof.subscriptionsmanager.di.AppModule
import com.vlatrof.subscriptionsmanager.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        // configure Dagger DI AppComponent
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(applicationContext = this))
            .build()

        // apply current night mode on app start
        applyNightMode(
            getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .getInt(NIGHT_MODE, DEFAULT_NIGHT_MODE)
        )
    }

    fun applyNightMode(nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    fun saveNightMode(nightMode: Int) {
        getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putInt(NIGHT_MODE, nightMode)
            .apply()
    }

    fun getCurrentNightMode(): Int {
        return getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            .getInt(NIGHT_MODE, -1)
    }

    fun saveLastCurrencyCode(currencyCode: String) {
        getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(LAST_CURRENCY_CODE, currencyCode)
            .apply()
    }

    fun getLastCurrencyCode(): String {
        return getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            .getString(LAST_CURRENCY_CODE, DEFAULT_CURRENCY_CODE)!!
    }

    companion object {

        // shared preferences keys
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val NIGHT_MODE = "NIGHT_MODE"
        const val LAST_CURRENCY_CODE = "LAST_CURRENCY_CODE"

        // shared preferences default values
        const val DEFAULT_NIGHT_MODE = MODE_NIGHT_FOLLOW_SYSTEM
        const val DEFAULT_CURRENCY_CODE = "USD"
    }
}
