package com.vlatrof.subscriptionsmanager.data.local

import com.vlatrof.subscriptionsmanager.data.local.room.dao.SubscriptionsDao
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubscriptionsLocalDataSource(private val subscriptionsDao: SubscriptionsDao) {

    val allSubscriptionsFlow: Flow<List<DataSubscription>> =
        subscriptionsDao.getAll().map { subscriptionsEntitiesList ->
            mutableListOf<DataSubscription>().apply {
                subscriptionsEntitiesList.forEach { subscriptionEntity ->
                    this.add(DataSubscription(subscriptionEntity))
                }
            }
        }

    fun getSubscriptionById(id: Int): DataSubscription {
        return DataSubscription(subscriptionsDao.getById(id))
    }

    fun insertSubscription(subscription: DataSubscription) {
        subscriptionsDao.insert(subscription.toSubscriptionEntity())
    }

    fun deleteAllSubscriptions() {
        subscriptionsDao.deleteAll()
    }

    fun deleteSubscriptionById(id: Int) {
        subscriptionsDao.deleteById(id)
    }

    fun updateSubscription(subscription: DataSubscription) {
        subscriptionsDao.update(subscription.toSubscriptionEntity())
    }

}