package com.vlatrof.subscriptionsmanager.data.repositories

import com.vlatrof.subscriptionsmanager.data.local.SubscriptionsLocalDataSource
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubscriptionsRepositoryImpl(

    private val subscriptionsLocalDataSource: SubscriptionsLocalDataSource

    ) : SubscriptionsRepository {

    override val allSubscriptionsFlow: Flow<List<DomainSubscription>> =
        subscriptionsLocalDataSource.allSubscriptionsFlow.map { dataSubscriptionsList ->
            mutableListOf<DomainSubscription>().apply {
                dataSubscriptionsList.forEach { dataSubscription ->
                    this.add(dataSubscription.toDomainSubscription())
                }
            }
        }

    override fun getSubscriptionById(id: Int): DomainSubscription {
        return subscriptionsLocalDataSource.getSubscriptionById(id).toDomainSubscription()
    }

    override fun insertSubscription(subscription: DomainSubscription) {
        subscriptionsLocalDataSource.insertSubscription(DataSubscription(subscription))
    }

    override fun deleteAllSubscriptions() {
        subscriptionsLocalDataSource.deleteAllSubscriptions()
    }

    override fun deleteSubscriptionById(id: Int) {
        subscriptionsLocalDataSource.deleteSubscriptionById(id)
    }

    override fun updateSubscription(subscription: DomainSubscription) {
        subscriptionsLocalDataSource.updateSubscription(DataSubscription(subscription))
    }

}