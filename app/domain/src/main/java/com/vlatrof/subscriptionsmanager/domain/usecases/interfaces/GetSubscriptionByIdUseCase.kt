package com.vlatrof.subscriptionsmanager.domain.usecases.interfaces

import com.vlatrof.subscriptionsmanager.domain.models.Subscription

interface GetSubscriptionByIdUseCase {

    operator fun invoke(id: Int): Subscription

}