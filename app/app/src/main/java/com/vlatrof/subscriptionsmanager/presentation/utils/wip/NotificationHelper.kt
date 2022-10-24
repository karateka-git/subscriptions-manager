package com.vlatrof.subscriptionsmanager.presentation.utils.wip

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.activities.MainActivity

class NotificationHelper(private val context: Context) {

    private val channelId = "SUBSCRIPTIONS_RENEWAL_ALERTS"
    private val channelName = "Subscription renewal alerts"
    private var notificationId = 1

    fun showRenewalNotification(subscription: Subscription) {

        val title = subscription.name
        val message = subscription.paymentCost.toString() + " " + subscription.paymentCurrency.currencyCode

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(channelId, channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)

        }

        val intent = Intent(context, MainActivity::class.java)
            .apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setTicker(title)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)

    }

}