package com.vlatrof.subscriptionsmanager.domain.usecases.getall

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import kotlinx.coroutines.flow.Flow

class GetAllSubscriptionsUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : GetAllSubscriptionsUseCase {

    override operator fun invoke(): Flow<List<Subscription>> {
        return subscriptionsRepository.allSubscriptionsFlow
    }
}
