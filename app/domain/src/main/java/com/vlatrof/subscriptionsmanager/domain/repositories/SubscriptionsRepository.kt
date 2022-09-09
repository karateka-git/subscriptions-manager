package com.vlatrof.subscriptionsmanager.domain.repositories

import kotlinx.coroutines.flow.Flow
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription

interface SubscriptionsRepository {

    val allSubscriptionsFlow: Flow<List<DomainSubscription>>

    fun insertSubscription(subscription: DomainSubscription)

    fun deleteAllSubscriptions()

}