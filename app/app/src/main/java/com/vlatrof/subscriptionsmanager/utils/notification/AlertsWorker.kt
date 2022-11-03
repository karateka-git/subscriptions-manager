package com.vlatrof.subscriptionsmanager.utils.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vlatrof.subscriptionsmanager.data.local.room.database.SubscriptionsRoomDatabase
import com.vlatrof.subscriptionsmanager.data.models.Subscription
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate

class AlertsWorker(

    private val context: Context,
    workerParameters: WorkerParameters

) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        coroutineScope {
            val deferred = async {
                SubscriptionsRoomDatabase
                    .getDatabase(context)
                    .getSubscriptionsDao()
                    .getAll()
            }

            val subscriptions = deferred.await()

            if (subscriptions.isEmpty()) return@coroutineScope
            subscriptions.forEach {
                val subscription = Subscription(it).toDomainSubscription()

                if (!subscription.alertEnabled) {
                    return@coroutineScope
                }

                if (subscription.nextRenewalDate + subscription.alertPeriod != LocalDate.now()) {
                    return@coroutineScope
                }

                NotificationHelper(context).showRenewalNotification(subscription)
            }
        }

        return Result.success()
    }
}
