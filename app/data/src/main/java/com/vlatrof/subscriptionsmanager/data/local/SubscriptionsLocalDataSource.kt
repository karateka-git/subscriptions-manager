package com.vlatrof.subscriptionsmanager.data.local

import com.vlatrof.subscriptionsmanager.data.local.room.dao.SubscriptionsDao
import com.vlatrof.subscriptionsmanager.data.utils.SubscriptionModelListMapper
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import com.vlatrof.subscriptionsmanager.data.utils.SubscriptionModelMapper

class SubscriptionsLocalDataSource(private val subscriptionsDao: SubscriptionsDao) {

    fun getAllSubscriptions(): List<DataSubscription> {
        val ls = subscriptionsDao.getAllSubscriptions()
        return SubscriptionModelListMapper.mapEntityToData(ls)
    }

    fun insertSubscription(subscription: DataSubscription) {
        subscriptionsDao.insert(SubscriptionModelMapper.mapDataToEntity(subscription))
    }

}