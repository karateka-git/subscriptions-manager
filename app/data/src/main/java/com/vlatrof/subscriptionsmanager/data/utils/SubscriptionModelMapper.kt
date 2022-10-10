package com.vlatrof.subscriptionsmanager.data.utils

import java.time.LocalDate
import java.time.Period
import java.util.*
import com.vlatrof.subscriptionsmanager.data.local.room.entities.SubscriptionEntity as EntitySubscription
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription

object SubscriptionModelMapper {

    fun mapEntityToData(entitySubscription: EntitySubscription): DataSubscription {

        return DataSubscription(
            id = entitySubscription.id ?: -1,
            name = entitySubscription.name,
            description = entitySubscription.description,
            paymentCost = entitySubscription.paymentCost,
            paymentCurrency = Currency.getInstance(entitySubscription.paymentCurrencyCode),
            startDate = LocalDate.parse(entitySubscription.startDate),
            renewalPeriod = Period.parse(entitySubscription.renewalPeriod),
            alertEnabled = entitySubscription.alertEnabled,
            alertPeriod = Period.parse(entitySubscription.alertPeriod),
        )

    }

    fun mapDataToEntity(dataSubscription: DataSubscription): EntitySubscription {

        return EntitySubscription(
            name = dataSubscription.name,
            description = dataSubscription.description,
            paymentCost = dataSubscription.paymentCost,
            paymentCurrencyCode = dataSubscription.paymentCurrency.currencyCode,
            renewalPeriod = dataSubscription.renewalPeriod.toString(),
            startDate = dataSubscription.startDate.toString(),
            alertPeriod = dataSubscription.alertPeriod.toString(),
        )

    }

    fun mapDataToDomain(dataSubscription: DataSubscription): DomainSubscription {

        return DomainSubscription(
            id = dataSubscription.id,
            name = dataSubscription.name,
            description = dataSubscription.description,
            paymentCost = dataSubscription.paymentCost,
            paymentCurrency = dataSubscription.paymentCurrency,
            startDate = dataSubscription.startDate,
            renewalPeriod = dataSubscription.renewalPeriod,
            alertEnabled = dataSubscription.alertEnabled,
            alertPeriod = dataSubscription.alertPeriod,
        )

    }

    fun mapDomainToData(domainSubscription: DomainSubscription): DataSubscription {

        return DataSubscription(
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

    }

}