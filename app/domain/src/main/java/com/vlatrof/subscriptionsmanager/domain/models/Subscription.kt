package com.vlatrof.subscriptionsmanager.domain.models

import java.time.LocalDate
import java.time.Period
import java.util.Currency

data class Subscription(

    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val paymentCost: Double = 0.0,
    val paymentCurrency: Currency = Currency.getInstance("USD"),
    val startDate: LocalDate = LocalDate.MIN,
    val renewalPeriod: Period = Period.ZERO,
    val nextRenewalDate: LocalDate = LocalDate.MIN,
    val alertEnabled: Boolean = false,
    val alertPeriod: Period = Period.ZERO

)
