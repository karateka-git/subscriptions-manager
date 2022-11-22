package com.vlatrof.subscriptionsmanager.domain.usecases.getbyid

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetSubscriptionByIdUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : GetSubscriptionByIdUseCase {

    override fun invoke(id: Int): Deferred<Subscription> {
        return CoroutineScope(Dispatchers.IO).async {
            subscriptionsRepository.getSubscriptionById(id)
        }
    }
}
