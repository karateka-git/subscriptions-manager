package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptiondetails

import androidx.lifecycle.LiveData
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.screens.base.BaseViewModel

abstract class SubscriptionDetailsViewModel : BaseViewModel() {

    abstract val subscriptionLiveData: LiveData<Subscription?>

    abstract val nameTitleLiveData: LiveData<String>

    abstract val nextRenewalTitleLiveData: LiveData<String>

    abstract val nameInputState: LiveData<InputState>

    abstract val costInputState: LiveData<InputState>

    abstract val currencyInputState: LiveData<InputState>

    abstract val startDateInputSelectionLiveData: LiveData<Long>

    abstract val buttonSaveState: LiveData<Boolean>

    abstract var currencyInputValue: String

    abstract var renewalPeriodValue: String

    abstract var alertInputValue: String

    abstract fun loadSubscriptionById(id: Int)

    abstract fun updateSubscription(subscription: Subscription)

    abstract fun deleteSubscriptionById(id: Int)

    abstract fun handleNewNameInputValue(newValue: String)

    abstract fun handleNewCostInputValue(newValue: String)

    abstract fun handleNewCurrencyValue(newValue: String)

    abstract fun handleNewStartDateValue(newValue: Long)

    abstract fun handleNewRenewalPeriodValue(newValue: String)
    
    abstract fun handleNewAlertValue(newValue: String)
}
