package com.vlatrof.subscriptionsmanager.domain.usecases.getbyid

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import kotlinx.coroutines.Deferred

interface GetSubscriptionByIdUseCase {

    suspend operator fun invoke(id: Int): Subscription
}
