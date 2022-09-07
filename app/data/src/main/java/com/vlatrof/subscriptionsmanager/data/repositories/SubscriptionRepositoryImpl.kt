package com.vlatrof.subscriptionsmanager.data.repositories

import com.vlatrof.subscriptionsmanager.data.utils.mappers.subscription.SubscriptionModelListMapper
import com.vlatrof.subscriptionsmanager.data.local.SubscriptionsLocalDataSource
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscriptionModel
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionRepository

class SubscriptionRepositoryImpl(

    private val subscriptionsLocalDataSource: SubscriptionsLocalDataSource

    ) : SubscriptionRepository {

    override fun getAllSubscriptions(): List<DomainSubscriptionModel> {

        val dataSubscriptions = subscriptionsLocalDataSource.getAllSubscriptions()
        return SubscriptionModelListMapper.mapDataToDomain(dataSubscriptions)

    }

}