package com.vlatrof.subscriptionsmanager.domain.usecases.interfaces

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import kotlinx.coroutines.flow.Flow

interface GetSubscriptionByIdUseCase {

//    suspend operator fun invoke(id: Int): Flow<Subscription>
    suspend operator fun invoke(id: Int): Subscription

}