package com.vlatrof.subscriptionsmanager.domain.usecases.deletebyid

import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository

class DeleteSubscriptionByIdUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : DeleteSubscriptionByIdUseCase {

    override operator fun invoke(id: Int) {
        subscriptionsRepository.deleteSubscriptionById(id)
    }
}
