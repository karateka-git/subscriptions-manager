package com.vlatrof.subscriptionsmanager.domain.usecases.getallsubscriptions

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import kotlinx.coroutines.flow.Flow

interface GetAllSubscriptionsUseCase {

    operator fun invoke(): Flow<List<Subscription>>

}