package com.vlatrof.subscriptionsmanager.data.local

import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import java.time.LocalDate
import java.time.Period
import java.util.Currency

class SubscriptionsLocalDataSource {

    fun getAllSubscriptions(): List<DataSubscription> {

        return listOf(

            DataSubscription(
                title = "Yandex Plus",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 249,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            DataSubscription(
                title = "Figma",
                startDate = LocalDate.of(2012, 9, 25),
                renewalPeriod = Period.ofYears(1),
                paymentCost = 5,
                paymentCurrency = Currency.getInstance("USD")
            ),

            DataSubscription(
                title = "Spotify Premium",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 149,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            DataSubscription(
                title = "Youtube Premium",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofWeeks(2),
                paymentCost = 199,
                paymentCurrency = Currency.getInstance("AUD")
            ),

            DataSubscription(
                title = "Tinkoff Pro",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 249,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            )

    }

}