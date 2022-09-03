package com.vlatrof.subscriptionsmanager.data.repositories

import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscriptionModel
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionRepository
import java.time.LocalDate
import java.time.Period
import java.util.*

class SubscriptionRepositoryImpl : SubscriptionRepository {

    override fun getAllSubscriptions(): List<DomainSubscriptionModel> {

        return listOf(

            com.vlatrof.subscriptionsmanager.domain.models.Subscription(
                title = "Yandex Plus",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 249,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            com.vlatrof.subscriptionsmanager.domain.models.Subscription(
                title = "Figma",
                startDate = LocalDate.of(2012, 9, 25),
                renewalPeriod = Period.ofYears(1),
                paymentCost = 5,
                paymentCurrency = Currency.getInstance("USD")
            ),

            com.vlatrof.subscriptionsmanager.domain.models.Subscription(
                title = "Spotify Premium",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 149,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            com.vlatrof.subscriptionsmanager.domain.models.Subscription(
                title = "Youtube Premium",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofWeeks(2),
                paymentCost = 199,
                paymentCurrency = Currency.getInstance("AUD")
            ),

            com.vlatrof.subscriptionsmanager.domain.models.Subscription(
                title = "Tinkoff Pro",
                startDate = LocalDate.of(2022, 8, 15),
                renewalPeriod = Period.ofMonths(1),
                paymentCost = 249,
                paymentCurrency = Currency.getInstance("RUB")
            ),

            )

    }

}