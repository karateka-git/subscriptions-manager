package com.vlatrof.subscriptionsmanager.domain.usecases.implementations

import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.DeleteAllSubscriptionsUseCase

class DeleteAllSubscriptionsUseCaseImpl(

    private val subscriptionsRepository: SubscriptionsRepository

) : DeleteAllSubscriptionsUseCase {

    override suspend operator fun invoke() {

        subscriptionsRepository.deleteAllSubscriptions()

    }

}