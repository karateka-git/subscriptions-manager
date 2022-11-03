package com.vlatrof.subscriptionsmanager.utils.notification

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
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
            repeatInterval = repeatInterval,
            TimeUnit.HOURS
        ).setInitialDelay(initialDelay.toLong(), TimeUnit.SECONDS)
            .build()

        // launch periodic work
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "subscriptionAlerts",
            ExistingPeriodicWorkPolicy.REPLACE,
            newWorkRequest
        )
    }
}
