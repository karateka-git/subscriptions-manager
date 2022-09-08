package com.vlatrof.subscriptionsmanager.domain.usecases

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import kotlinx.coroutines.flow.Flow

class GetAllSubscriptionsUseCase(private val subscriptionsRepository: SubscriptionsRepository) {

    fun execute(): Flow<List<Subscription>> = subscriptionsRepository.allSubscriptionsFlow

}