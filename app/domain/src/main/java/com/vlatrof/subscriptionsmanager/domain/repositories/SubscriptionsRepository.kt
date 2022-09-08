package com.vlatrof.subscriptionsmanager.domain.repositories

import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription

interface SubscriptionsRepository {
    
    fun getAllSubscriptions(): List<DomainSubscription>
    fun insertSubscription(subscription: DomainSubscription)

}