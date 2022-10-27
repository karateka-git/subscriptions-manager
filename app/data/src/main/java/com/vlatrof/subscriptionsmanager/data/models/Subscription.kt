package com.vlatrof.subscriptionsmanager.data.models

import java.util.Currency
import java.time.LocalDate
import java.time.Period
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription
import com.vlatrof.subscriptionsmanager.data.local.room.entities.SubscriptionEntity

data class Subscription(

    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val paymentCost: Double = 0.0,
    val paymentCurrency: Currency = Currency.getInstance("USD"),
    val startDate: LocalDate = LocalDate.MIN,
    val renewalPeriod: Period = Period.ZERO,
    val alertEnabled: Boolean = false,
    val alertPeriod: Period = Period.ZERO,

    ) {

    constructor(subscriptionEntity: SubscriptionEntity) : this (

        id = subscriptionEntity.id!!,
        name = subscriptionEntity.name,
        description = subscriptionEntity.description,
        paymentCost = subscriptionEntity.paymentCost,
        paymentCurrency = Currency.getInstance(subscriptionEntity.paymentCurrencyCode),
        startDate = LocalDate.parse(subscriptionEntity.startDate),
        renewalPeriod = Period.parse(subscriptionEntity.renewalPeriod),
        alertEnabled = subscriptionEntity.alertEnabled,
        alertPeriod = Period.parse(subscriptionEntity.alertPeriod),

    )

    constructor(domainSubscription: DomainSubscription) : this (

        id = domainSubscription.id,
        name = domainSubscription.name,
        description = domainSubscription.description,
        paymentCost = domainSubscription.paymentCost,
        paymentCurrency = domainSubscription.paymentCurrency,
        startDate = domainSubscription.startDate,
        renewalPeriod = domainSubscription.renewalPeriod,
        alertEnabled = domainSubscription.alertEnabled,
        alertPeriod = domainSubscription.alertPeriod,

    )

    fun toDomainSubscription(): DomainSubscription {

        val currentDate = LocalDate.now()
        val startDate = this.startDate
        val renewalPeriod = this.renewalPeriod
        var nextRenewalDate: LocalDate = LocalDate.from(startDate)
        while (nextRenewalDate < currentDate) {
            nextRenewalDate = renewalPeriod.addTo(nextRenewalDate) as LocalDate
        }

        return DomainSubscription(
            id = this.id,
            name = this.name,
            description = this.description,
            paymentCost = this.paymentCost,
            paymentCurrency = this.paymentCurrency,
            startDate = this.startDate,
            renewalPeriod = this.renewalPeriod,
            nextRenewalDate = nextRenewalDate,
            alertEnabled = this.alertEnabled,
            alertPeriod = this.alertPeriod,
        )

    }

    fun toSubscriptionEntity(): SubscriptionEntity {

        return SubscriptionEntity(
            id = if (this.id != -1) this.id else null,
            name = this.name,
            description = this.description,
            paymentCost = this.paymentCost,
            paymentCurrencyCode = this.paymentCurrency.currencyCode,
            startDate = this.startDate.toString(),
            renewalPeriod = this.renewalPeriod.toString(),
            alertEnabled = this.alertEnabled,
            alertPeriod = this.alertPeriod.toString(),
        )

    }

}