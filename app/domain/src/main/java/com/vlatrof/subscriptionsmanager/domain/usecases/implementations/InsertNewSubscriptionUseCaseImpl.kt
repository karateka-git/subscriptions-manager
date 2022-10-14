package com.vlatrof.subscriptionsmanager.domain.usecases.implementations

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.InsertNewSubscriptionUseCase

class InsertNewSubscriptionUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

    ) : InsertNewSubscriptionUseCase {

    override operator fun invoke(subscription: Subscription) {

        subscriptionsRepository.insertSubscription(subscription)

    }

}