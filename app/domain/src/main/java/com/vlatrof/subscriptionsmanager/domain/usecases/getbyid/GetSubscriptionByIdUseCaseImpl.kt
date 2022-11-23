package com.vlatrof.subscriptionsmanager.domain.usecases.getbyid

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import kotlinx.coroutines.*

class GetSubscriptionByIdUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : GetSubscriptionByIdUseCase {

    override suspend fun invoke(id: Int): Subscription = withContext(Dispatchers.IO) {
        subscriptionsRepository.getSubscriptionById(id)
    }
}
