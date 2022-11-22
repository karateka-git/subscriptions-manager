package com.vlatrof.subscriptionsmanager.domain.usecases.getbyid

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import kotlinx.coroutines.Deferred

interface GetSubscriptionByIdUseCase {

    operator fun invoke(id: Int): Deferred<Subscription>
}
