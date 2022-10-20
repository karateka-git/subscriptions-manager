package com.vlatrof.subscriptionsmanager.domain.usecases.interfaces

interface DeleteSubscriptionByIdUseCase {

    operator fun invoke(id: Int)

}