package com.vlatrof.subscriptionsmanager.domain.usecases

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import java.time.LocalDate
import java.time.Period
import java.util.*

class GetAllSubscriptionsUseCase() {

    fun execute(): List<Subscription> {

        //TODO Not implemented yet

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