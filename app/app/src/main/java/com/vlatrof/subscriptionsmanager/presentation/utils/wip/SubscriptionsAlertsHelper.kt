package com.vlatrof.subscriptionsmanager.presentation.utils.wip

//class SubscriptionsAlertsHelper(private val context: Context) {
//
//    fun launchPeriodicAlerts(subscription: Subscription) {
//
//        val repeatInterval = Duration.from(subscription.renewalPeriod)
//        val initialDelay = Duration.from(
//            Period.between(LocalDate.now(), subscription.nextRenewalDate)
//        )
//
//        val newWorkRequest =
//            PeriodicWorkRequestBuilder<AlertsWorker>(repeatInterval = repeatInterval)
//                .setInitialDelay(initialDelay)
//
//    }
//
//    fun stopPeriodicAlerts(subscription: Subscription) {
//
//        WorkManager.getInstance(context).cancelUniqueWork("couponValidatorWorker")
//
//    }
//
//    class AlertsWorker(
//
//        private val context: Context,
//        private val workerParameters: WorkerParameters
//
//    ) : Worker(context, workerParameters) {
//
//        override fun doWork(): Result {
//
//            NotificationHelper(context).createNotification(
//                inputData.getString("title").toString(),
//                inputData.getString("message").toString(),
//            )
//
//            return Result.success()
//
//        }
//
//    }
//
//    private class NotificationHelper(private val context: Context) {
//
//        private val channelId = "REMINDER_CHANNEL_ID"
//        private val notificationId = 1
//
//        // function responsible for creating a notifications channel
//        // through which the NotificationManager would deliver our reminders
//        private fun createNotificationChannel() {
//
//            // check if the user's phone is Android version Oreo and above;
//            // this is because we don’t need to create a notification channel for older versions.
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
//
//            val channel = NotificationChannel(
//                channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
//                .apply {
//                    description = "Reminder Channel Description"
//                }
//
//            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
//                .createNotificationChannel(channel)
//
//        }
//
//        fun createNotification(title: String, message: String) {
//
//            createNotificationChannel()
//
//            // Create an Intent that calls the MainActivity when it runs.
//            // Adding an intent is to start up the app when the user clicks on the notification
//            // in the menu tray.
//            val intent = Intent(context, MainActivity::class.java)
//                .apply {
//
//                    // Intent.FLAG_ACTIVITY_NEW_TASK will ensure the MainActivity opens up as a
//                    // new Task on the history stack. It comes up as the root and appears as new in
//                    // the history stack.
//                    //
//                    // Intent.FLAG_ACTIVITY_CLEAR_TASK will cause any existing task associated with
//                    // the activity to clear out before the activity starts.
//                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//                }
//
//            // Since we are not launching the intent immediately, create a pending intent and pass
//            // the intent created to it. PendingIntent in itself is a description of an Intent and
//            // what action it’s to perform. It gives an external application (like NotificationManager
//            // in our case) access to launch tasks for us as if we were launching them now, with the
//            // same set of permissions we would use. We call getActivity, which means the
//            // PendingIntent is to start a newActivity when it launches.
//            val pendingIntent =
//                PendingIntent.getActivity(context, 0, intent, 0)
//
//            // We create the notification object by using NotificationCompat.Builder
//            // and pass in the channelID
//            val notification = NotificationCompat.Builder(context, channelId)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.drawable.ic_baseline_arrow_back)
//                .setTicker(message)
//                .setContentIntent(pendingIntent)
//                .build()
//
//            // We create the notification using the NotificationManagerCompat,
//            // passing in the NotificationID and the notification Object we created before
//            NotificationManagerCompat.from(context).notify(notificationId, notification)
//
//        }
//
//    }
//
//}