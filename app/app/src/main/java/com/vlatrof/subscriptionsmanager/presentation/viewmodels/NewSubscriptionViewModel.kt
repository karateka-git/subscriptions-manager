package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.InsertNewSubscriptionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import java.util.Currency

enum class InputState(val stringResourceId: Int) {
    INITIAL (stringResourceId = R.string.new_subscription_field_error_none),
    CORRECT (stringResourceId = R.string.new_subscription_field_error_none),
    WRONG (stringResourceId = R.string.new_subscription_field_error_wrong),
    EMPTY (stringResourceId = R.string.new_subscription_field_error_empty)
}

class NewSubscriptionViewModel (

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase,

    ) : ViewModel() {

    // to restore menu values
    var currencyInputSelection = ""
    var renewalPeriodInputSelection = ""
    var alertInputSelection = ""
    val startDateInputSelection = MutableLiveData(
        MaterialDatePicker.todayInUtcMilliseconds()
    )

    // validating inputs states
    val nameInputState = MutableLiveData(InputState.INITIAL)
    val costInputState = MutableLiveData(InputState.INITIAL)
    val currencyInputState = MutableLiveData(InputState.INITIAL)

    val buttonCreateState = MutableLiveData(false)

    private val availableCurrencies = Currency.getAvailableCurrencies()

    fun validateNameInput(newValue: String) {
        nameInputState.value =
            if (newValue.isEmpty()) InputState.EMPTY
            else InputState.CORRECT
    }

    fun validateCostInput(newValue: String) {

        if (newValue.isEmpty()) {
            costInputState.value = InputState.EMPTY
            return
        }

        try {
            newValue.toDouble()
        } catch (nfe: NumberFormatException) {
            costInputState.value = InputState.WRONG
            return
        }

        costInputState.value = InputState.CORRECT

    }

    fun validateCurrencyInput(newValue: String) {

        if (newValue.isEmpty()) {
            currencyInputState.value = InputState.EMPTY
            return
        }

        try {
            if (!availableCurrencies.contains(Currency.getInstance(newValue))) {
                currencyInputState.value = InputState.WRONG
                return
            }
        } catch (exception: IllegalArgumentException) {
            currencyInputState.value = InputState.WRONG
            return
        }

        currencyInputState.value = InputState.CORRECT

    }

    fun validateCreateButton() {
        buttonCreateState.value =
            nameInputState.value == InputState.CORRECT
            && costInputState.value == InputState.CORRECT
            && currencyInputState.value == InputState.CORRECT
    }

    fun insertNewSubscription(subscription: Subscription) {
        viewModelScope.launch(ioDispatcher) {
            insertNewSubscriptionUseCase(subscription)
        }
    }

}