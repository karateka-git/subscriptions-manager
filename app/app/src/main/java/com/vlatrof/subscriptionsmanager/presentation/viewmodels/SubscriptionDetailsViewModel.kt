package com.vlatrof.subscriptionsmanager.presentation.viewmodels

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.DeleteSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.GetSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.interfaces.UpdateSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.presentation.fragments.SubscriptionDetailsFragment
import com.vlatrof.subscriptionsmanager.presentation.utils.Parser
import com.vlatrof.subscriptionsmanager.presentation.utils.RenewalPeriodOptionsHolder
import com.vlatrof.subscriptionsmanager.presentation.utils.getFirstKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Currency

class SubscriptionDetailsViewModel(

    private val resources: Resources,
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase,
    private val deleteSubscriptionByIdUseCase: DeleteSubscriptionByIdUseCase,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,

    ) : BaseViewModel() {

    val subscriptionLiveData = MutableLiveData<Subscription>()

    private val availableCurrencies = Currency.getAvailableCurrencies()

    // value holders
    var currencyInputValue: String      = ""
    var alertInputValue: String         = ""
    private var renewalPeriodValue: Period = Period.parse("P1D")
    val nameTitleLiveData = MutableLiveData("")
    val nextRenewalTitleLiveData = MutableLiveData("")
    val startDateInputSelectionLiveData = MutableLiveData(
        MaterialDatePicker.todayInUtcMilliseconds()
    )

    // state holders
    val nameInputState = MutableLiveData(InputState.INITIAL)
    val costInputState = MutableLiveData(InputState.INITIAL)
    val currencyInputState = MutableLiveData(InputState.INITIAL)
    val buttonSaveState = MutableLiveData(false)

    fun handleNewNameTitleValue(newValue: String) {
        nameTitleLiveData.value = newValue
    }

    fun handleNewNextRenewalDate(nextRenewalDate: LocalDate) {
        nextRenewalTitleLiveData.value = generateNextRenewalTitleStr(nextRenewalDate)
    }

    fun handleNewNameInputValue(newValue: String) {
        nameInputState.value = validateNameValue(newValue)
        buttonSaveState.value = validateSaveButtonState()
    }

    fun handleNewCostInputValue(newValue: String) {
        costInputState.value = validateCostValue(newValue)
        buttonSaveState.value = validateSaveButtonState()
    }

    fun handleNewCurrencyValue(newValue: String) {
        currencyInputValue = newValue
        currencyInputState.value = validateCurrencyValue(newValue)
        buttonSaveState.value = validateSaveButtonState()
    }

    fun handleNewStartDateValue(newValue: Long) {
        startDateInputSelectionLiveData.value = newValue
        val newStartDate = Parser.parseLocalDateFromUTCMilliseconds(newValue)
        val newNextRenewalDate = calculateNextRenewalDate(newStartDate, renewalPeriodValue)
        handleNewNextRenewalDate(newNextRenewalDate)
    }

    fun handleNewRenewalPeriodValue(newValue: String) {
        renewalPeriodValue = Period.parse(
            RenewalPeriodOptionsHolder(resources).options.getFirstKey(newValue)
        )
        val startDate =
            Parser.parseLocalDateFromUTCMilliseconds(startDateInputSelectionLiveData.value!!)
        val newNextRenewalDate = calculateNextRenewalDate(startDate, renewalPeriodValue)
        handleNewNextRenewalDate(newNextRenewalDate)
    }

    private fun calculateNextRenewalDate(startDate: LocalDate, renewalPeriod: Period): LocalDate {

        val currentDate = LocalDate.now()
        var nextRenewalDate: LocalDate = LocalDate.from(startDate)
        while (nextRenewalDate < currentDate) {
            nextRenewalDate = renewalPeriod.addTo(nextRenewalDate) as LocalDate
        }
        return nextRenewalDate

    }

    fun handleNewAlertValue(newValue: String) {
        alertInputValue = newValue
    }

    private fun validateNameValue(newValue: String) : InputState {
        if (newValue.isEmpty()) return InputState.EMPTY
        if (newValue.isBlank()) return InputState.WRONG
        return InputState.CORRECT
    }

    private fun validateCostValue(newValue: String): InputState {

        if (newValue.isEmpty()) return InputState.EMPTY

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

        if (subscriptionLiveData.value != null) {
            return
        }

        if (id == SubscriptionDetailsFragment.ARGUMENT_SUBSCRIPTION_ID_DEFAULT_VALUE) {
            throw IllegalArgumentException("Empty fragment argument: ${SubscriptionDetailsFragment.ARGUMENT_SUBSCRIPTION_ID_TAG}")
        }

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

    fun deleteSubscriptionById(id: Int) {

        viewModelScope.launch(ioDispatcher) {
            deleteSubscriptionByIdUseCase(id)
        }

    }

    fun updateSubscription(subscription: Subscription) {

        viewModelScope.launch(ioDispatcher) {
            updateSubscriptionUseCase(subscription)
        }

    }

    private fun generateNextRenewalTitleStr(nextRenewalDate: LocalDate): String {

        val nextRenewalTitle = resources.getString(
            R.string.subscription_details_tv_next_renewal_title)

        val nextRenewalDateFormatted = when (nextRenewalDate) {
            LocalDate.now() -> {
                resources.getString(R.string.today)
            }
            LocalDate.now().plusDays(1) -> {
                resources.getString(R.string.tomorrow)
            }
            else -> {
                nextRenewalDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
            }
        }

        return "$nextRenewalTitle $nextRenewalDateFormatted"

    }

    fun restoreRenewalPeriodValue(): String {

        return RenewalPeriodOptionsHolder(resources).options[renewalPeriodValue.toString()]!!

    }

}