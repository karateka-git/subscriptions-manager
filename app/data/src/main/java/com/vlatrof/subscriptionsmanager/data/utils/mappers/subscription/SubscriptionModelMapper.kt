package com.vlatrof.subscriptionsmanager.data.utils.mappers.subscription

import java.time.LocalDate
import java.time.Period
import java.util.*
import com.vlatrof.subscriptionsmanager.data.local.SubscriptionEntity as EntitySubscription
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription

object SubscriptionModelMapper {

    fun mapEntityToData(entitySubscription: EntitySubscription): DataSubscription {

        return DataSubscription(
            id = entitySubscription.id,
            title = entitySubscription.title,
            startDate = LocalDate.parse(entitySubscription.startDate),
            renewalPeriod = Period.parse(entitySubscription.renewalPeriod),
            paymentCost = entitySubscription.paymentCost,
            paymentCurrency = Currency.getInstance(entitySubscription.paymentCurrencyCode)
        )

    }

    fun mapDataToEntity(dataSubscription: DataSubscription): EntitySubscription {

        return EntitySubscription(
            id = dataSubscription.id,
            title = dataSubscription.title,
            startDate = dataSubscription.startDate.toString(),
            renewalPeriod = dataSubscription.renewalPeriod.toString(),
            paymentCost = dataSubscription.paymentCost,
            paymentCurrencyCode = dataSubscription.paymentCurrency.currencyCode
        )

    }

    fun mapDataToDomain(dataSubscription: DataSubscription): DomainSubscription {

        return DomainSubscription(
            id = dataSubscription.id,
            title = dataSubscription.title,
            startDate = dataSubscription.startDate,
            renewalPeriod = dataSubscription.renewalPeriod,
            paymentCost = dataSubscription.paymentCost,
            paymentCurrency = dataSubscription.paymentCurrency
        )

    }

    fun mapDomainToData(domainSubscription: DomainSubscription): DataSubscription {

        return DataSubscription(
            id = domainSubscription.id,
            title = domainSubscription.title,
            startDate = domainSubscription.startDate,
            renewalPeriod = domainSubscription.renewalPeriod,
            paymentCost = domainSubscription.paymentCost,
            paymentCurrency = domainSubscription.paymentCurrency
        )

    }

}