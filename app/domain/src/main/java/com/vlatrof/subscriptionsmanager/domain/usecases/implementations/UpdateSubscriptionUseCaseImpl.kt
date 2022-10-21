package com.vlatrof.subscriptionsmanager.domain.usecases.implementations

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.UpdateSubscriptionUseCase

class UpdateSubscriptionUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

    ): UpdateSubscriptionUseCase {

    override fun invoke(subscription: Subscription) {

        subscriptionsRepository.updateSubscription(subscription)

    }

}