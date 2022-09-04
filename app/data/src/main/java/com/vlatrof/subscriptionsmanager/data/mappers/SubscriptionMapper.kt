package com.vlatrof.subscriptionsmanager.data.mappers

import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscriptionModel
import com.vlatrof.subscriptionsmanager.data.storages.models.Subscription as StorageSubscriptionModel

object SubscriptionModelMapper {

    fun mapToDomain(storageSubscription: StorageSubscriptionModel): DomainSubscriptionModel {

        return DomainSubscriptionModel(
            id = storageSubscription.id,
            title = storageSubscription.title,
            startDate = storageSubscription.startDate,
            renewalPeriod = storageSubscription.renewalPeriod,
            paymentCost = storageSubscription.paymentCost,
            paymentCurrency = storageSubscription.paymentCurrency
        )

    }

    fun mapToStorage(domainSubscription: DomainSubscriptionModel): StorageSubscriptionModel {

        return StorageSubscriptionModel(
            id = domainSubscription.id,
            title = domainSubscription.title,
            startDate = domainSubscription.startDate,
            renewalPeriod = domainSubscription.renewalPeriod,
            paymentCost = domainSubscription.paymentCost,
            paymentCurrency = domainSubscription.paymentCurrency
        )

    }

    fun mapListToDomain(storageSubscriptionList: List<StorageSubscriptionModel>): List<DomainSubscriptionModel> {

        val domainSubscriptionList = mutableListOf<DomainSubscriptionModel>()

        storageSubscriptionList.forEach {
            domainSubscriptionList.add(mapToDomain(it))
        }

        return domainSubscriptionList

    }

    fun mapListToStorage(domainSubscriptionList: List<DomainSubscriptionModel>): List<StorageSubscriptionModel> {

        val storageSubscriptionList = mutableListOf<StorageSubscriptionModel>()

        domainSubscriptionList.forEach {
            storageSubscriptionList.add(mapToStorage(it))
        }

        return storageSubscriptionList

    }

}