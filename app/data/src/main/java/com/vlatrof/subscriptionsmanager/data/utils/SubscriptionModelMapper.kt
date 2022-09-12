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
            startDate = LocalDate.parse(entitySubscription.startDate),
            renewalPeriod = Period.parse(entitySubscription.renewalPeriod),
            paymentCost = entitySubscription.paymentCost,
            paymentCurrency = Currency.getInstance(entitySubscription.paymentCurrencyCode)
        )

    }

    fun mapDataToEntity(dataSubscription: DataSubscription): EntitySubscription {

        return EntitySubscription(
            name = dataSubscription.name,
            description = dataSubscription.description,
            startDate = dataSubscription.startDate.toString(),
            renewalPeriod = dataSubscription.renewalPeriod.toString(),
            paymentCost = dataSubscription.paymentCost,
            paymentCurrencyCode = dataSubscription.paymentCurrency.currencyCode
        )

    }

    fun mapDataToDomain(dataSubscription: DataSubscription): DomainSubscription {

        return DomainSubscription(
            id = dataSubscription.id,
            name = dataSubscription.name,
            description = dataSubscription.description,
            startDate = dataSubscription.startDate,
            renewalPeriod = dataSubscription.renewalPeriod,
            paymentCost = dataSubscription.paymentCost,
            paymentCurrency = dataSubscription.paymentCurrency
        )

    }

    fun mapDomainToData(domainSubscription: DomainSubscription): DataSubscription {

        return DataSubscription(
            id = domainSubscription.id,
            name = domainSubscription.name,
            description = domainSubscription.description,
            startDate = domainSubscription.startDate,
            renewalPeriod = domainSubscription.renewalPeriod,
            paymentCost = domainSubscription.paymentCost,
            paymentCurrency = domainSubscription.paymentCurrency
        )

    }

}