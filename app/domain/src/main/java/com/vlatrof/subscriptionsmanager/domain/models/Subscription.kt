package com.vlatrof.subscriptionsmanager.domain.models

import java.util.Currency
import java.time.LocalDate
import java.time.Period

data class Subscription (
    val title: String = "",
    val startDate: LocalDate = LocalDate.now(),
    val renewalPeriod: Period = Period.ZERO,
    val paymentCost: Int = 0,
    val paymentCurrency: Currency = Currency.getInstance("USD")
)