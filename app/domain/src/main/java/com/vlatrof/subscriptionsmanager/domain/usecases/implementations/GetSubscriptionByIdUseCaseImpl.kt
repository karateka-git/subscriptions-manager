package com.vlatrof.subscriptionsmanager.domain.usecases.implementations

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetSubscriptionByIdUseCase

class GetSubscriptionByIdUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : GetSubscriptionByIdUseCase {

    override fun invoke(id: Int): Subscription {
        return subscriptionsRepository.getSubscriptionById(id)
    }

}