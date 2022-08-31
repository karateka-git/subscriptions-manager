package com.vlatrof.subscriptionsmanager.domain.models

import java.util.Currency
import java.time.LocalDate
import java.time.Period

data class Subscription (
    val title: String = "",
    val startDate: LocalDate,
    val renewalPeriod: Period,
    val paymentCost: Int = 0,
    val paymentCurrency: Currency
)