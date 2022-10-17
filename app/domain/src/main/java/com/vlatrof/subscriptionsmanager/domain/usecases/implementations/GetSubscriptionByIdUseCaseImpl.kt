package com.vlatrof.subscriptionsmanager.domain.usecases.implementations

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetSubscriptionByIdUseCase
import kotlinx.coroutines.flow.Flow

class GetSubscriptionByIdUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : GetSubscriptionByIdUseCase {

//    override suspend fun invoke(id: Int): Flow<Subscription> {
//       return subscriptionsRepository.getSubscriptionById(id)
//    }

    override suspend fun invoke(id: Int): Subscription {
        return subscriptionsRepository.getSubscriptionById(id)
    }

}