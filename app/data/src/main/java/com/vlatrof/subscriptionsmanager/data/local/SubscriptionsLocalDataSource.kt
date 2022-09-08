package com.vlatrof.subscriptionsmanager.data.local

import com.vlatrof.subscriptionsmanager.data.local.room.dao.SubscriptionsDao
import com.vlatrof.subscriptionsmanager.data.utils.SubscriptionModelListMapper
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import com.vlatrof.subscriptionsmanager.data.utils.SubscriptionModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubscriptionsLocalDataSource(private val subscriptionsDao: SubscriptionsDao) {

    val allSubscriptionsFlow: Flow<List<DataSubscription>> =
        subscriptionsDao.getAllSubscriptions().map { subscriptionsEntities ->
            SubscriptionModelListMapper.mapEntityToData(subscriptionsEntities)
        }

    fun insertSubscription(subscription: DataSubscription) {
        subscriptionsDao.insert(SubscriptionModelMapper.mapDataToEntity(subscription))
    }

}