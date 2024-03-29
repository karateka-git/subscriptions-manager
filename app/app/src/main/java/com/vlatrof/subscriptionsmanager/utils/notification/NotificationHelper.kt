package com.vlatrof.subscriptionsmanager.utils.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.activity.MainActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotificationHelper(private val context: Context) {

    private val channelId = "SUBSCRIPTIONS_RENEWAL_ALERTS"
    private val channelName = context.resources.getString(
        R.string.renewal_notification_channel_name
    )

    fun showRenewalNotification(subscription: Subscription) {
        NotificationManagerCompat.from(context).notify(
            subscription.id,
            createRenewalNotification(subscription)
        )
    }

    private fun createRenewalNotification(subscription: Subscription): Notification {
        // title str
        val title = context.getString(R.string.renewal_notification_title, subscription.name)

        // message date str
        val dateStr = when (subscription.nextRenewalDate) {
            LocalDate.now() -> { context.getString(R.string.today).lowercase() }
            LocalDate.now().plusDays(1) -> { context.getString(R.string.tomorrow).lowercase() }
            else -> {
                subscription.nextRenewalDate.format(DateTimeFormatter.ofPattern("dd MMMM"))
            }
        }

        // completed message str
        val message = context.getString(
            R.string.renewal_notification_message,
            subscription.name,
            dateStr,
            subscription.paymentCost.toString(),
            subscription.paymentCurrency.currencyCode
        )

        // submit notification channel if needed by version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }

        // create pending intent to open app by click on notification
        val intent = Intent(context, MainActivity::class.java)
            .apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK }
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(context, 0, intent, 0)
        }

        // build notification
        return NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setTicker(message)
            .setSmallIcon(R.drawable.ic_notification_small)
            .setColor(ResourcesCompat.getColor(context.resources, R.color.green, null))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}
