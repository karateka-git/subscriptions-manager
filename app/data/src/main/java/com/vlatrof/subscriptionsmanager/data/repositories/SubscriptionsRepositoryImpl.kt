package com.vlatrof.subscriptionsmanager.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.vlatrof.subscriptionsmanager.data.utils.SubscriptionModelListMapper
import com.vlatrof.subscriptionsmanager.data.local.SubscriptionsLocalDataSource
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import com.vlatrof.subscriptionsmanager.data.utils.SubscriptionModelMapper
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository

class SubscriptionsRepositoryImpl(

    private val subscriptionsLocalDataSource: SubscriptionsLocalDataSource

    ) : SubscriptionsRepository {

    override fun getAllSubscriptions(): List<DomainSubscription> {
        val ls = subscriptionsLocalDataSource.getAllSubscriptions()
        return SubscriptionModelListMapper.mapDataToDomain(ls)
    }

    override fun insertSubscription(subscription: DomainSubscription) {
        val s = SubscriptionModelMapper.mapDomainToData(subscription)
        subscriptionsLocalDataSource.insertSubscription(s)
    }


}