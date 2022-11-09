package com.vlatrof.subscriptionsmanager.di

import android.content.Context
import android.content.res.Resources
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.domain.usecases.deletebyid.DeleteSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getallflow.GetAllSubscriptionsFlowUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getbyid.GetSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnew.InsertNewSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.update.UpdateSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.presentation.screens.newsubscription.NewSubscriptionViewModelFactory
import com.vlatrof.subscriptionsmanager.presentation.screens.options.OptionsViewModelFactory
import com.vlatrof.subscriptionsmanager.presentation.screens.subscriptiondetails.SubscriptionDetailsViewModelFactory
import com.vlatrof.subscriptionsmanager.presentation.screens.subscriptionslist.SubscriptionsViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    fun provideApp(): App {
        return applicationContext as App
    }

    @Provides
    fun provideResources(): Resources {
        return applicationContext.resources
    }

    @Provides
    fun provideSubscriptionsViewModelFactory(
        getAllSubscriptionsFlowUseCase: GetAllSubscriptionsFlowUseCase
    ): SubscriptionsViewModelFactory {
        return SubscriptionsViewModelFactory(
            getAllSubscriptionsFlowUseCase = getAllSubscriptionsFlowUseCase
        )
    }

    @Provides
    fun provideNewSubscriptionViewModelFactory(
        application: App,
        insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase
    ): NewSubscriptionViewModelFactory {
        return NewSubscriptionViewModelFactory(
            application = application,
            ioDispatcher = Dispatchers.IO,
            insertNewSubscriptionUseCase = insertNewSubscriptionUseCase
        )
    }

    @Provides
    fun provideSubscriptionDetailsViewModelFactory(
        resources: Resources,
        getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
        updateSubscriptionUseCase: UpdateSubscriptionUseCase,
        deleteSubscriptionByIdUseCase: DeleteSubscriptionByIdUseCase
    ): SubscriptionDetailsViewModelFactory {
        return SubscriptionDetailsViewModelFactory(
            resources = resources,
            getSubscriptionByIdUseCase = getSubscriptionByIdUseCase,
            updateSubscriptionUseCase = updateSubscriptionUseCase,
            deleteSubscriptionByIdUseCase = deleteSubscriptionByIdUseCase,
            mainDispatcher = Dispatchers.Main,
            ioDispatcher = Dispatchers.IO
        )
    }

    @Provides
    fun provideOptionsViewModelFactory(
        application: App
    ): OptionsViewModelFactory {
        return OptionsViewModelFactory(
            application = application,
            ioDispatcher = Dispatchers.IO
        )
    }
}
