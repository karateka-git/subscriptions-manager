package com.vlatrof.subscriptionsmanager.domain.repositories

import kotlinx.coroutines.flow.Flow
import com.vlatrof.subscriptionsmanager.domain.models.Subscription

interface SubscriptionsRepository {

    val allSubscriptionsFlow: Flow<List<Subscription>>

//    suspend fun getSubscriptionById(id: Int): Flow<Subscription>
    suspend fun getSubscriptionById(id: Int): Subscription

    suspend fun insertSubscription(subscription: Subscription)

    suspend fun deleteAllSubscriptions()

}