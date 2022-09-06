package com.vlatrof.subscriptionsmanager.data.utils

import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscriptionModel
import com.vlatrof.subscriptionsmanager.data.local.SubscriptionEntity as DataSubscriptionModel

object SubscriptionModelMapper {

    fun mapDataToDomain(storageSubscription: DataSubscriptionModel): DomainSubscriptionModel {

        return DomainSubscriptionModel(
            id = storageSubscription.id,
            title = storageSubscription.title,
            startDate = storageSubscription.startDate,
            renewalPeriod = storageSubscription.renewalPeriod,
            paymentCost = storageSubscription.paymentCost,
            paymentCurrency = storageSubscription.paymentCurrency
        )

    }

    fun mapDomainToData(domainSubscription: DomainSubscriptionModel): DataSubscriptionModel {

        return DataSubscriptionModel(
            id = domainSubscription.id,
            title = domainSubscription.title,
            startDate = domainSubscription.startDate,
            renewalPeriod = domainSubscription.renewalPeriod,
            paymentCost = domainSubscription.paymentCost,
            paymentCurrency = domainSubscription.paymentCurrency
        )

    }

    fun mapListDataToDomain(storageSubscriptionList: List<DataSubscriptionModel>): List<DomainSubscriptionModel> {

        val domainSubscriptionList = mutableListOf<DomainSubscriptionModel>()

        storageSubscriptionList.forEach {
            domainSubscriptionList.add(mapDataToDomain(it))
        }

        return domainSubscriptionList

    }

    fun mapListDomainToData(domainSubscriptionList: List<DomainSubscriptionModel>): List<DataSubscriptionModel> {

        val storageSubscriptionList = mutableListOf<DataSubscriptionModel>()

        domainSubscriptionList.forEach {
            storageSubscriptionList.add(mapDomainToData(it))
        }

        return storageSubscriptionList

    }

}