package com.vlatrof.subscriptionsmanager.domain.usecases

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository

class InsertNewSubscriptionUseCase(private val subscriptionsRepository: SubscriptionsRepository) {

    fun execute(subscription: Subscription) {
        return subscriptionsRepository.insertSubscription(subscription)
    }

}