package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetSubscriptionByIdUseCase
import kotlinx.coroutines.*
import java.lang.NumberFormatException
import java.util.Currency

class SubscriptionDetailsViewModel(

    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,

    ) : BaseViewModel() {

    val subscriptionLiveData = MutableLiveData<Subscription>()

    private val availableCurrencies = Currency.getAvailableCurrencies()

    // value holders
    var nameInputValueLiveData = MutableLiveData("")
    var descriptionInputValue   = ""
    var costInputValue          = ""
    var currencyInputValue      = ""
    var renewalPeriodInputValue = ""
    var alertInputValue         = ""
    val startDateInputSelection = MutableLiveData(
        MaterialDatePicker.todayInUtcMilliseconds()
    )

    // state holders
    val nameInputState = MutableLiveData(InputState.INITIAL)
    val costInputState = MutableLiveData(InputState.INITIAL)
    val currencyInputState = MutableLiveData(InputState.INITIAL)
    val buttonSaveState = MutableLiveData(false)

    fun handleNewNameInputValue(newValue: String) {
        nameInputValueLiveData.value = newValue
        nameInputState.value = validateNameValue(newValue)
        buttonSaveState.value = validateSaveButtonState()
    }

    fun handleNewDescriptionInputValue(newValue: String) {
        descriptionInputValue = newValue
    }

    fun handleNewCostInputValue(newValue: String) {
        costInputValue = newValue
        costInputState.value = validateCostValue(newValue)
        buttonSaveState.value = validateSaveButtonState()
    }

    fun handleNewCurrencyValue(newValue: String) {
        currencyInputValue = newValue
        currencyInputState.value = validateCurrencyValue(newValue)
        buttonSaveState.value = validateSaveButtonState()
    }

    fun handleNewStartDateValue(newValue: Long) {
        startDateInputSelection.value = newValue
    }

    fun handleNewRenewalPeriodValue(newValue: String) {
        renewalPeriodInputValue = newValue
    }

    fun handleNewAlertValue(newValue: String) {
        alertInputValue = newValue
    }

    private fun validateNameValue(newValue: String) : InputState {
        return if (newValue.isEmpty())
            InputState.EMPTY
        else
            InputState.CORRECT
    }

    private fun validateCostValue(newValue: String): InputState {
        if (newValue.isEmpty()) {
            return InputState.EMPTY
        }
        try {
            newValue.toDouble()
        } catch (nfe: NumberFormatException) {
            return InputState.WRONG
        }

        return InputState.CORRECT
    }

    private fun validateCurrencyValue(newValue: String): InputState {

        if (newValue.isEmpty()) {
            return InputState.EMPTY
        }

        try {
            if (!availableCurrencies.contains(Currency.getInstance(newValue))) {
                return InputState.WRONG
            }
        } catch (exception: IllegalArgumentException) {
            return InputState.WRONG
        }

        return InputState.CORRECT

    }

    private fun validateSaveButtonState(): Boolean {
        return nameInputState.value == InputState.CORRECT
                && costInputState.value == InputState.CORRECT
                && currencyInputState.value == InputState.CORRECT
    }

    fun loadSubscriptionById(id: Int) {

        if (subscriptionLiveData.value != null) return

        // Create and start new coroutine with Dispatchers.Main;
        // We need coroutine to use await() suspend function and receive future value from db;
        // We need Dispatchers.Main because only on main thread we have access to save data into
        // LiveData.value
        viewModelScope.launch(mainDispatcher) {

            // Create and start new coroutine with Dispatchers.IO;
            // We use Dispatchers.IO to work on background thread to load data from Room Database;
            // async() builder will create the coroutine and return its related DeferredJob;
            val deferredLoadingJob = viewModelScope.async(ioDispatcher) {
                return@async getSubscriptionByIdUseCase(id)
            }

            // we use await() on this deferred job to receive completed value in future
            subscriptionLiveData.value = deferredLoadingJob.await()

        }

    }

}