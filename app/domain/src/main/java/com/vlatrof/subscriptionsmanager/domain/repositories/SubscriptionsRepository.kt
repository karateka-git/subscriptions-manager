package com.vlatrof.subscriptionsmanager.domain.repositories

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionsRepository {

    val allSubscriptionsFlow: Flow<List<Subscription>>

    fun getSubscriptionById(id: Int): Subscription

    fun insertSubscription(subscription: Subscription)

    fun deleteAllSubscriptions()

    fun deleteSubscriptionById(id: Int)

    fun updateSubscription(subscription: Subscription)
}
