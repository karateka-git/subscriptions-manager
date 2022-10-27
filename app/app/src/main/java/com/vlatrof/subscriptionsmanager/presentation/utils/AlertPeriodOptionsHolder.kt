package com.vlatrof.subscriptionsmanager.presentation.utils

import android.content.res.Resources
import com.vlatrof.subscriptionsmanager.R

class AlertPeriodOptionsHolder(resources: Resources) {

    val options = linkedMapOf (
        "NONE" to resources.getString(R.string.option_alert_period_none),
        "P0D"  to resources.getString(R.string.option_alert_period_same_day),
        "P-1D" to resources.getString(R.string.option_alert_period_one_day_before),
        "P-2D" to resources.getString(R.string.option_alert_period_two_days_before),
        "P-7D" to resources.getString(R.string.option_alert_period_one_week_before),
    )

    val defaultValue = options["NONE"]!!

}