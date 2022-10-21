package com.vlatrof.subscriptionsmanager.domain.usecases.interfaces

import com.vlatrof.subscriptionsmanager.domain.models.Subscription

interface UpdateSubscriptionUseCase {

    operator fun invoke(subscription: Subscription)

}