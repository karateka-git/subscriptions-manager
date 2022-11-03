package com.vlatrof.subscriptionsmanager.domain.usecases.insertnew

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository

class InsertNewSubscriptionUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : InsertNewSubscriptionUseCase {

    override operator fun invoke(subscription: Subscription) {
        subscriptionsRepository.insertSubscription(subscription)
    }
}
