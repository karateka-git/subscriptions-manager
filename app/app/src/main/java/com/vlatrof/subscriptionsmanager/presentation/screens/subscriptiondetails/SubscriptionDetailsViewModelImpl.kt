package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptiondetails

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.domain.usecases.deletebyid.DeleteSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.getbyid.GetSubscriptionByIdUseCase
import com.vlatrof.subscriptionsmanager.domain.usecases.update.UpdateSubscriptionUseCase
import com.vlatrof.subscriptionsmanager.utils.Parser
import com.vlatrof.subscriptionsmanager.utils.RenewalPeriodOptions
import com.vlatrof.subscriptionsmanager.utils.getFirstKey
import java.lang.NumberFormatException
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriptionDetailsViewModelImpl(

    private val resources: Resources,
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase,
    private val deleteSubscriptionByIdUseCase: DeleteSubscriptionByIdUseCase,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : SubscriptionDetailsViewModel() {

    override val subscriptionLiveData = MutableLiveData<Subscription?>()

    // стандартный Pair может быть заменён на свою модель, к примеру ValueAndStateEditText(value: String, State: InputState)
    // ну и название переменных тоже стоит изменить :)
    override val nameTitleLiveData: MutableLiveData<Pair<String, InputState>> = MutableLiveData(Pair("", InputState.Initial))

    override val nextRenewalTitleLiveData = MutableLiveData("")

    override val costInputState = MutableLiveData<InputState>(InputState.Initial)

    override val currencyInputState = MutableLiveData<InputState>(InputState.Initial)

    override val startDateInputSelectionLiveData =
        MutableLiveData(MaterialDatePicker.todayInUtcMilliseconds())

    override val buttonSaveState = MutableLiveData(false)

    override var currencyInputValue: String = ""

    override var renewalPeriodValue: String = ""

    override var alertInputValue: String = ""

    private val availableCurrencies = Currency.getAvailableCurrencies()

    private var currentRenewalPeriod: Period = Period.parse("P1D")

    override fun loadSubscriptionById(id: Int) {
        viewModelScope.launch(mainDispatcher) {
            subscriptionLiveData.value = getSubscriptionByIdUseCase(id).await()
        }
    }

    override fun updateSubscription(subscription: Subscription) {
        viewModelScope.launch(ioDispatcher) {
            updateSubscriptionUseCase(subscription)
        }
    }
    
    override fun deleteSubscriptionById(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            deleteSubscriptionByIdUseCase(id)
        }
    }

    override fun handleNewNameInputValue(newValue: String) {
        nameTitleLiveData.value = Pair(newValue, validateNameValue(newValue))
        validateSaveButtonState()
    }

    override fun handleNewCostInputValue(newValue: String) {
        costInputState.value = validateCostValue(newValue)
        validateSaveButtonState()
    }

    override fun handleNewCurrencyValue(newValue: String) {
        currencyInputValue = newValue
        currencyInputState.value = validateCurrencyValue(newValue)
        validateSaveButtonState()
    }

    override fun handleNewStartDateValue(newValue: Long) {
        startDateInputSelectionLiveData.value = newValue
        val newStartDate = Parser.parseLocalDateFromUTCMilliseconds(newValue)
        val newNextRenewalDate = calculateNextRenewalDate(newStartDate, currentRenewalPeriod)
        nextRenewalTitleLiveData.value = generateNextRenewalTitleStr(newNextRenewalDate)
    }

    override fun handleNewRenewalPeriodValue(newValue: String) {
        renewalPeriodValue = newValue
        currentRenewalPeriod = Period.parse(
            RenewalPeriodOptions(resources).options.getFirstKey(newValue)
        )
        val startDate =
            Parser.parseLocalDateFromUTCMilliseconds(startDateInputSelectionLiveData.value!!)
        val newNextRenewalDate = calculateNextRenewalDate(startDate, currentRenewalPeriod)
        nextRenewalTitleLiveData.value = generateNextRenewalTitleStr(newNextRenewalDate)
    }

    override fun handleNewAlertValue(newValue: String) {
        alertInputValue = newValue
    }

    private fun validateNameValue(newValue: String): InputState {
        if (newValue.isEmpty()) return InputState.Empty
        if (newValue.isBlank()) return InputState.Wrong
        return InputState.Correct
    }

    private fun validateCostValue(newValue: String): InputState {
        if (newValue.isEmpty()) return InputState.Empty

        try {
            newValue.toDouble()
        } catch (nfe: NumberFormatException) {
            return InputState.Wrong
        }

        return InputState.Correct
    }

    private fun validateCurrencyValue(newValue: String): InputState {
        if (newValue.isEmpty()) {
            return InputState.Empty
        }

        try {
            if (!availableCurrencies.contains(Currency.getInstance(newValue))) {
                return InputState.Wrong
            }
        } catch (exception: IllegalArgumentException) {
            return InputState.Wrong
        }

        return InputState.Correct
    }

    // лучше чтобы значение buttonSaveState изменялось внутри метода за это отвечающего, к чему передача наверх?)
    private fun validateSaveButtonState() {
        buttonSaveState.value = nameTitleLiveData.value?.second == InputState.Correct &&
            costInputState.value == InputState.Correct &&
            currencyInputState.value == InputState.Correct
    }

    private fun calculateNextRenewalDate(startDate: LocalDate, renewalPeriod: Period): LocalDate {
        val currentDate = LocalDate.now()
        var nextRenewalDate: LocalDate = LocalDate.from(startDate)
        while (nextRenewalDate < currentDate) {
            nextRenewalDate = renewalPeriod.addTo(nextRenewalDate) as LocalDate
        }
        return nextRenewalDate
    }

    // TODO: тут не должно быть обращения к ресурсам и получения String?!
    private fun generateNextRenewalTitleStr(nextRenewalDate: LocalDate): String {
        val nextRenewalTitle = resources.getString(
            R.string.subscription_details_tv_next_renewal_title
        )

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
}
