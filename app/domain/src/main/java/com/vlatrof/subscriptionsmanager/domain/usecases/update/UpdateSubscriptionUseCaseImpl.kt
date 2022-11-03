package com.vlatrof.subscriptionsmanager.domain.usecases.update

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository

class UpdateSubscriptionUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : UpdateSubscriptionUseCase {

    override fun invoke(subscription: Subscription) {
        subscriptionsRepository.updateSubscription(subscription)
    }
}
