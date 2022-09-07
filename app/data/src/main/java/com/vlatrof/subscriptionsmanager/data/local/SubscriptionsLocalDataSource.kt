package com.vlatrof.subscriptionsmanager.data.local

import java.time.LocalDate
import java.time.Period
import java.util.Currency

class SubscriptionsLocalDataSource {

    fun getAllSubscriptions(): List<SubscriptionEntity> {

        return listOf(

            SubscriptionEntity(
                title = "Yandex Plus",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 249,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            SubscriptionEntity(
                title = "Figma",
                startDate = LocalDate.of(2012, 9, 25),
                renewalPeriod = Period.ofYears(1),
                paymentCost = 5,
                paymentCurrency = Currency.getInstance("USD")
            ),

            SubscriptionEntity(
                title = "Spotify Premium",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 149,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            SubscriptionEntity(
                title = "Youtube Premium",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofWeeks(2),
                paymentCost = 199,
                paymentCurrency = Currency.getInstance("AUD")
            ),

            SubscriptionEntity(
                title = "Tinkoff Pro",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 249,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            )

    }

}