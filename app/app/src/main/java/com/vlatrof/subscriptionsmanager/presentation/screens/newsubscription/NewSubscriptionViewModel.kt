package com.vlatrof.subscriptionsmanager.presentation.screens.newsubscription

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.insertnew.InsertNewSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.presentation.screens.base.BaseViewModel
import java.lang.NumberFormatException
import java.util.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewSubscriptionViewModel(

    private val application: App,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val insertNewSubscriptionUseCase: InsertNewSubscriptionUseCase

) : BaseViewModel() {

    // value holders
    var currencyInputValue = ""
    var renewalPeriodInputSelection = ""
    var alertInputSelection = ""
    val startDateInputSelection = MutableLiveData(
        MaterialDatePicker.todayInUtcMilliseconds()
    )

    // state holders
    val nameInputState = MutableLiveData<InputState>(InputState.Initial)
    val costInputState = MutableLiveData<InputState>(InputState.Initial)
    val currencyInputState = MutableLiveData<InputState>(InputState.Initial)
    val buttonCreateState = MutableLiveData(false)

    private val availableCurrencies = Currency.getAvailableCurrencies()

    fun validateNameInput(newValue: String) {
        nameInputState.value =
            if (newValue.isEmpty()) InputState.Empty
            else if (newValue.isBlank()) InputState.Wrong
            else InputState.Correct
    }

    fun validateCostInput(newValue: String) {
        if (newValue.isEmpty()) {
            costInputState.value = InputState.Empty
            return
        }

        try {
            newValue.toDouble()
        } catch (nfe: NumberFormatException) {
            costInputState.value = InputState.Wrong
            return
        }

        costInputState.value = InputState.Correct
    }

    fun validateCurrencyInput(newValue: String) {
        if (newValue.isEmpty()) {
            currencyInputState.value = InputState.Empty
            return
        }

        try {
            if (!availableCurrencies.contains(Currency.getInstance(newValue))) {
                currencyInputState.value = InputState.Wrong
                return
            }
        } catch (exception: IllegalArgumentException) {
            currencyInputState.value = InputState.Wrong
            return
        }

        currencyInputState.value = InputState.Correct
    }

    fun updateCreateButtonState() {
        buttonCreateState.value =
            nameInputState.value == InputState.Correct &&
            costInputState.value == InputState.Correct &&
            currencyInputState.value == InputState.Correct
    }

    fun insertNewSubscription(subscription: Subscription) {
        viewModelScope.launch(ioDispatcher) {
            insertNewSubscriptionUseCase(subscription)
        }
    }

    fun getLastCurrencyCode(): String {
        return application.getLastCurrencyCode()
    }

    fun saveLastCurrencyCode(currencyCode: String) {
        application.saveLastCurrencyCode(currencyCode)
    }
}
