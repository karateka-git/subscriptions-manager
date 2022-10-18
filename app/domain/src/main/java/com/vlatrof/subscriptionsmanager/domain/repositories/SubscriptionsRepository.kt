package com.vlatrof.subscriptionsmanager.domain.repositories

import kotlinx.coroutines.flow.Flow
import com.vlatrof.subscriptionsmanager.domain.models.Subscription

interface SubscriptionsRepository {

    val allSubscriptionsFlow: Flow<List<Subscription>>

    fun getSubscriptionById(id: Int): Subscription

    fun insertSubscription(subscription: Subscription)

    fun deleteAllSubscriptions()

}