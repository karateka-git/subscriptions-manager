package com.vlatrof.subscriptionsmanager.di

import com.vlatrof.subscriptionsmanager.domain.repositories.SubscriptionsRepository
import com.vlatrof.subscriptionsmanager.domain.usecases.deletebyid.DeleteSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.deletebyid.DeleteSubscriptionByIdUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.getallflow.GetAllSubscriptionsFlowUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getallflow.GetAllSubscriptionsFlowUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.getbyid.GetSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getbyid.GetSubscriptionByIdUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnew.InsertNewSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnew.InsertNewSubscriptionUseCaseImpl
import com.vlatrof.subscriptionsmanager.domain.usecases.update.UpdateSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.update.UpdateSubscriptionUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideDeleteSubscriptionByIdUseCase(
        subscriptionsRepository: SubscriptionsRepository
    ): DeleteSubscriptionByIdUseCase {
        return DeleteSubscriptionByIdUseCaseImpl(
            subscriptionsRepository = subscriptionsRepository
        )
    }

    @Provides
    fun provideGetAllSubscriptionsFlowUseCase(
        subscriptionsRepository: SubscriptionsRepository
    ): GetAllSubscriptionsFlowUseCase {
        return GetAllSubscriptionsFlowUseCaseImpl(
            subscriptionsRepository = subscriptionsRepository
        )
    }

    @Provides
    fun provideGetSubscriptionByIdUseCase(
        subscriptionsRepository: SubscriptionsRepository
    ): GetSubscriptionByIdUseCase {
        return GetSubscriptionByIdUseCaseImpl(
            subscriptionsRepository = subscriptionsRepository
        )
    }

    @Provides
    fun provideInsertNewSubscriptionUseCase(
        subscriptionsRepository: SubscriptionsRepository
    ): InsertNewSubscriptionUseCase {
        return InsertNewSubscriptionUseCaseImpl(
            subscriptionsRepository = subscriptionsRepository
        )
    }

    @Provides
    fun provideUpdateSubscriptionUseCase(
        subscriptionsRepository: SubscriptionsRepository
    ): UpdateSubscriptionUseCase {
        return UpdateSubscriptionUseCaseImpl(
            subscriptionsRepository = subscriptionsRepository
        )
    }
}
