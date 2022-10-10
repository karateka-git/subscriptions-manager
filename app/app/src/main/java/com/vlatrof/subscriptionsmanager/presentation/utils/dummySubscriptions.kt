package com.vlatrof.subscriptionsmanager.presentation.utils

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import java.time.LocalDate
import java.time.Period
import java.util.*

val dummySubscriptionsList = listOf(

    Subscription(
        name = "Yandex Plus",
        description = "Some useful subscription",
        startDate = LocalDate.of(2022, 8, 15),
        renewalPeriod = Period.ofMonths(1),
        paymentCost = 249.0,
        paymentCurrency = Currency.getInstance("RUB")
    ),

    Subscription(
        name = "Figma",
        description = "Some useful subscription",
        startDate = LocalDate.of(2012, 9, 25),
        renewalPeriod = Period.ofYears(1),
        paymentCost = 5.0,
        paymentCurrency = Currency.getInstance("USD")
    ),

    Subscription(
        name = "Spotify Premium",
        description = "Some useful subscription",
        startDate = LocalDate.of(2022, 8, 15),
        renewalPeriod = Period.ofMonths(1),
        paymentCost = 149.0,
        paymentCurrency = Currency.getInstance("RUB")
    ),

    Subscription(
        name = "Youtube Premium",
        description = "Some useful subscription",
        startDate = LocalDate.of(2022, 8, 15),
        renewalPeriod = Period.ofWeeks(2),
        paymentCost = 199.0,
        paymentCurrency = Currency.getInstance("AUD")
    ),

    Subscription(
        name = "Tinkoff Pro",
        description = "Some useful subscription",
        startDate = LocalDate.of(2022, 8, 15),
        renewalPeriod = Period.ofMonths(1),
        paymentCost = 249.0,
        paymentCurrency = Currency.getInstance("RUB")
    ),

    )