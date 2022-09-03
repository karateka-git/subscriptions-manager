package com.vlatrof.subscriptionsmanager.domain.usecases

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionRepository

class GetAllSubscriptionsUseCase(private val subscriptionRepository: SubscriptionRepository) {

    fun execute(): List<Subscription> {

        return subscriptionRepository.getAllSubscriptions()

    }

}