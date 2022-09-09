package com.vlatrof.subscriptionsmanager.domain.usecases.insertnewsubscription

import com.vlatrof.subscriptionsmanager.domain.models.Subscription

interface InsertNewSubscriptionUseCase {

    operator fun invoke(subscription: Subscription)

}