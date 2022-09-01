package com.vlatrof.subscriptionsmanager.domain.models

import java.util.Currency
import java.time.LocalDate
import java.time.Period

data class Subscription (
    val id: Int = -1,
    val title: String = "",
    val startDate: LocalDate = LocalDate.MIN,
    val renewalPeriod: Period = Period.ZERO,
    val paymentCost: Int = 0,
    val paymentCurrency: Currency = Currency.getInstance("USD")
)