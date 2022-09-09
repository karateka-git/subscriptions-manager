package com.vlatrof.subscriptionsmanager.data.repositories

import com.vlatrof.subscriptionsmanager.data.utils.SubscriptionModelListMapper
import com.vlatrof.subscriptionsmanager.data.local.SubscriptionsLocalDataSource
import com.vlatrof.subscriptionsmanager.data.utils.SubscriptionModelMapper
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubscriptionsRepositoryImpl(

    private val subscriptionsLocalDataSource: SubscriptionsLocalDataSource

    ) : SubscriptionsRepository {

    override val allSubscriptionsFlow: Flow<List<DomainSubscription>> =
        subscriptionsLocalDataSource.allSubscriptionsFlow.map { dataSubscriptions ->
            SubscriptionModelListMapper.mapDataToDomain(dataSubscriptions)
        }

    override fun insertSubscription(subscription: DomainSubscription) {
        val s = SubscriptionModelMapper.mapDomainToData(subscription)
        subscriptionsLocalDataSource.insertSubscription(s)
    }

    override fun deleteAllSubscriptions() {
        subscriptionsLocalDataSource.deleteAllSubscriptions()
    }

}