package com.vlatrof.subscriptionsmanager.domain.usecases.getallflow

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import kotlinx.coroutines.flow.Flow

class GetAllSubscriptionsFlowUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : GetAllSubscriptionsFlowUseCase {

    override operator fun invoke(): Flow<List<Subscription>> {
        return subscriptionsRepository.allSubscriptionsFlow
    }
}
