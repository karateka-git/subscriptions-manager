package com.vlatrof.subscriptionsmanager.utils

import android.content.res.Resources
import com.vlatrof.subscriptionsmanager.R

class RenewalPeriodOptionsHolder(resources: Resources) {

    val options = linkedMapOf(
        "P1D" to resources.getString(R.string.option_renewal_period_daily),
        "P7D" to resources.getString(R.string.option_renewal_period_weekly),
        "P14D" to resources.getString(R.string.option_renewal_period_every_two_weeks),
        "P1M" to resources.getString(R.string.option_renewal_period_monthly),
        "P3M" to resources.getString(R.string.option_renewal_period_every_three_months),
        "P6M" to resources.getString(R.string.option_renewal_period_every_six_months),
        "P1Y" to resources.getString(R.string.option_renewal_period_yearly)
    )

    val defaultValue = options["P1M"]!!
}
