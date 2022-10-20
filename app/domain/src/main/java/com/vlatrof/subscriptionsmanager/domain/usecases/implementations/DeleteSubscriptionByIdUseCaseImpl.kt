package com.vlatrof.subscriptionsmanager.domain.usecases.implementations

import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.DeleteSubscriptionByIdUseCase

class DeleteSubscriptionByIdUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

    ) : DeleteSubscriptionByIdUseCase {

    override operator fun invoke(id: Int) {

        subscriptionsRepository.deleteSubscriptionById(id)

    }

}