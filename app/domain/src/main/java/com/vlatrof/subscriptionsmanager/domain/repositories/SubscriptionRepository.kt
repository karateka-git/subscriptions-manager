package com.vlatrof.subscriptionsmanager.domain.repositories

import com.vlatrof.subscriptionsmanager.domain.models.Subscription

interface SubscriptionRepository {

    fun getAllSubscriptions(): List<Subscription>

}