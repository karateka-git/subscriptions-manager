package com.vlatrof.subscriptionsmanager.domain.usecases.insertnew

import com.vlatrof.subscriptionsmanager.domain.models.Subscription

interface InsertNewSubscriptionUseCase {

    operator fun invoke(subscription: Subscription)

}