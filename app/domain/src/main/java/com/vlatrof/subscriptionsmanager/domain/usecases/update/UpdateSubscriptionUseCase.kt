package com.vlatrof.subscriptionsmanager.domain.usecases.update

import com.vlatrof.subscriptionsmanager.domain.models.Subscription

interface UpdateSubscriptionUseCase {

    operator fun invoke(subscription: Subscription)
}
