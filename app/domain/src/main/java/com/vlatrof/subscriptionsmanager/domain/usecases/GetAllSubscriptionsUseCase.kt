package com.vlatrof.subscriptionsmanager.domain.usecases

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository

class GetAllSubscriptionsUseCase(private val subscriptionsRepository: SubscriptionsRepository) {

    fun execute(): List<Subscription> = subscriptionsRepository.getAllSubscriptions()

}