package com.vlatrof.subscriptionsmanager.data.storages.roomstorage

import com.vlatrof.subscriptionsmanager.data.storages.models.Subscription
import java.time.LocalDate
import java.time.Period
import java.util.*

class SubscriptionRoomStorage {
    
    fun foo(): List<Subscription> {

        return listOf(

            Subscription(
                title = "Yandex Plus",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 249,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            Subscription(
                title = "Figma",
                startDate = LocalDate.of(2012, 9, 25),
                renewalPeriod = Period.ofYears(1),
                paymentCost = 5,
                paymentCurrency = Currency.getInstance("USD")
            ),

            Subscription(
                title = "Spotify Premium",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 149,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            Subscription(
                title = "Youtube Premium",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofWeeks(2),
                paymentCost = 199,
                paymentCurrency = Currency.getInstance("AUD")
            ),

            Subscription(
                title = "Tinkoff Pro",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 249,
                paymentCurrency = Currency.getInstance("RUB")
            ),

        )

    }
    
}