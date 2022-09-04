package com.vlatrof.subscriptionsmanager.data.repositories

import com.vlatrof.subscriptionsmanager.data.mappers.SubscriptionModelMapper
import com.vlatrof.subscriptionsmanager.data.storages.SubscriptionStorage
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscriptionModel
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionRepository

class SubscriptionRepositoryImpl(private val subscriptionStorage: SubscriptionStorage) : SubscriptionRepository {

    override fun getAllSubscriptions(): List<DomainSubscriptionModel> {

        val storageSubscriptions = subscriptionStorage.getAllSubscriptions()

        return SubscriptionModelMapper.mapListToDomain(storageSubscriptions)

    }


}