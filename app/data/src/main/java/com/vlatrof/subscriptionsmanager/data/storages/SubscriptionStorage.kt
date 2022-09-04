package com.vlatrof.subscriptionsmanager.data.storages

import com.vlatrof.subscriptionsmanager.data.storages.models.Subscription

interface SubscriptionStorage {

    fun getAllSubscriptions(): List<Subscription>

}