package com.vlatrof.subscriptionsmanager.domain.usecases.getbyid

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository

class GetSubscriptionByIdUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : GetSubscriptionByIdUseCase {

    override fun invoke(id: Int): Subscription {
        return subscriptionsRepository.getSubscriptionById(id)
    }
}
