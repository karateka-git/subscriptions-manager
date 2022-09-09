package com.vlatrof.subscriptionsmanager.domain.usecases.deleteallsubscriptions

import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository

class DeleteAllSubscriptionsUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : DeleteAllSubscriptionsUseCase {

    override operator fun invoke() {

        subscriptionsRepository.deleteAllSubscriptions()

    }

}