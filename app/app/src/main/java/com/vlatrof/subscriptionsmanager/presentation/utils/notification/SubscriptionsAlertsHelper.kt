package com.vlatrof.subscriptionsmanager.presentation.utils.notification

import android.content.Context
import androidx.work.*
import com.vlatrof.subscriptionsmanager.data.local.room.database.SubscriptionsRoomDatabase
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class SubscriptionsAlertsHelper(private val context: Context) {

    fun launchAlertsWorker() {

        // repeat interval in hours
        val repeatInterval = 24L

        // initial delay in seconds (to the next time point 12.00)
        val currentTime = LocalTime.now().toSecondOfDay()
        val alertTime = LocalTime.of(12, 0).toSecondOfDay()
        val initialDelay = if (currentTime < alertTime) {
            alertTime - currentTime
        } else {
            86400 - currentTime + alertTime
        }

        // create work request
        val newWorkRequest = PeriodicWorkRequestBuilder<AlertsWorker>(
            repeatInterval = repeatInterval, TimeUnit.HOURS
        ).setInitialDelay(initialDelay.toLong(), TimeUnit.SECONDS)
            .build()

        // launch periodic work
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "subscriptionAlerts", ExistingPeriodicWorkPolicy.REPLACE, newWorkRequest
        )

    }

}