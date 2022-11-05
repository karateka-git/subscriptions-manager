package com.vlatrof.subscriptionsmanager.domain.usecases.getallflow

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import kotlinx.coroutines.flow.Flow

interface GetAllSubscriptionsFlowUseCase {

    operator fun invoke(): Flow<List<Subscription>>
}
