package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.InsertNewSubscriptionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class InputState {
    DEFAULT, EMPTY, WRONG, CORRECT
}

class NewSubscriptionViewModel (

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase

    ) : ViewModel() {

    var inputCurrencySelection = ""
    var inputRenewalPeriodSelection = ""
    var inputAlertSelection = ""

    val inputNameState = MutableLiveData(InputState.DEFAULT)
    val inputCostState = MutableLiveData(InputState.DEFAULT)
    val inputCurrencyState = MutableLiveData(InputState.DEFAULT)

    val buttonCreateEnabledLiveData = MutableLiveData(false)

    val startDateSelectionLiveData = MutableLiveData(
        MaterialDatePicker.todayInUtcMilliseconds()
    )

    fun insertNewSubscription(subscription: Subscription) {
        viewModelScope.launch(ioDispatcher) {
            insertNewSubscriptionUseCase(subscription)
        }
    }

}