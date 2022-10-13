package com.vlatrof.subscriptionsmanager.data.local

import com.vlatrof.subscriptionsmanager.data.local.room.dao.SubscriptionsDao
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubscriptionsLocalDataSource(private val subscriptionsDao: SubscriptionsDao) {

    val allSubscriptionsFlow: Flow<List<DataSubscription>> =
        subscriptionsDao.getAllSubscriptions().map { subscriptionsEntitiesList ->
            mutableListOf<DataSubscription>().apply {
                subscriptionsEntitiesList.forEach { subscriptionEntity ->
                    this.add(DataSubscription(subscriptionEntity))
                }
            }
        }

    fun insertSubscription(subscription: DataSubscription) {
        subscriptionsDao.insert(subscription.toSubscriptionEntity())
    }

    fun deleteAllSubscriptions() {
        subscriptionsDao.deleteAllSubscriptions()
    }

}