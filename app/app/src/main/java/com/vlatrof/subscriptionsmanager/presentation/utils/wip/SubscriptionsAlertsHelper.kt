package com.vlatrof.subscriptionsmanager.presentation.utils.wip

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.asLiveData
import androidx.work.*
import com.vlatrof.subscriptionsmanager.data.local.room.database.SubscriptionsRoomDatabase
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class SubscriptionsAlertsHelper(private val application: Application) {

    fun launchAlertsWorker() {

        // repeat interval
//        val repeatInterval = 24L

        // initial delay
        val currentTime = LocalTime.now().toSecondOfDay()
        val alertTime = LocalTime.of(12, 0).toSecondOfDay()

//        val initialDelay = if (currentTime < alertTime) {
//            alertTime - currentTime
//        } else {
//            86400 - currentTime + alertTime
//        }

        val initialDelay = 5
        val repeatInterval = 5L

        // create work request
        val newWorkRequest = PeriodicWorkRequestBuilder<AlertsWorker>(
            repeatInterval = repeatInterval, TimeUnit.MINUTES
        ).setInitialDelay(initialDelay.toLong(), TimeUnit.SECONDS)
            .build()

        // launch periodic work
        WorkManager.getInstance(application).enqueueUniquePeriodicWork(
            "subscriptionAlerts", ExistingPeriodicWorkPolicy.REPLACE, newWorkRequest
        )

    }

    class AlertsWorker (

        private val context: Context,
        private val workerParameters: WorkerParameters

    ) : CoroutineWorker(context, workerParameters) {

        override suspend fun doWork(): Result {

            coroutineScope {

                val deferred = async {
                    SubscriptionsRoomDatabase
                        .getDatabase(context)
                        .getSubscriptionsDao()
                        .getAlls()
                }

                val subscriptions = deferred.await()

                Log.d("worker", "deferred awaited")

                NotificationHelper(context).showRenewalNotification(
                    title = subscriptions[0].name,
                    message = subscriptions[0].name
                )

            }

            return Result.success()

        }

    }

}