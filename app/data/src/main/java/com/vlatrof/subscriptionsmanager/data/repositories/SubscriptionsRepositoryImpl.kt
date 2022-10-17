package com.vlatrof.subscriptionsmanager.data.repositories

import com.vlatrof.subscriptionsmanager.data.local.SubscriptionsLocalDataSource
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

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

//    override suspend fun getSubscriptionById(id: Int): Flow<DomainSubscription> {
//        return subscriptionsLocalDataSource.getSubscriptionById(id).transform {
//                dataSubscription -> dataSubscription.toDomainSubscription()
//        }
//    }

    override suspend fun getSubscriptionById(id: Int): DomainSubscription {
        return subscriptionsLocalDataSource.getSubscriptionById(id).toDomainSubscription()
    }

    override suspend fun insertSubscription(subscription: DomainSubscription) {
        subscriptionsLocalDataSource.insertSubscription(DataSubscription(subscription))
    }

    override suspend fun deleteAllSubscriptions() {
        subscriptionsLocalDataSource.deleteAllSubscriptions()
    }

}