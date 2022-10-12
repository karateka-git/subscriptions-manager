package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentNewSubscriptionBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.utils.hideKeyboard
import com.vlatrof.subscriptionsmanager.presentation.utils.parseLocalDateFromUTCMilliseconds
import com.vlatrof.subscriptionsmanager.presentation.utils.parseXmlResourceMap
import com.vlatrof.subscriptionsmanager.presentation.utils.round
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.InputState
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.NewSubscriptionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.NumberFormatException
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Currency

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private lateinit var binding: FragmentNewSubscriptionBinding
    private val newSubscriptionViewModel by viewModel<NewSubscriptionViewModel>()
    private val availableCurrencies = Currency.getAvailableCurrencies().toTypedArray()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewSubscriptionBinding.bind(view)
        setupGoBackButton()
        setupNameInputField()
        setupCostInputField()
        setupStartDateInputField()
        setupCreateButton()
        startValidationObserve()
    }

    override fun onResume() {
        super.onResume()
        setupCurrencyInputField()
        setupRenewalPeriodInputField()
        setupAlertInputField()
    }

    private fun setupGoBackButton() {
        binding.btnNewSubscriptionGoBack.setOnClickListener {
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun setupNameInputField() {

        val nameFieldContainer = binding.tilNewSubscriptionName
        val nameField = binding.tietNewSubscriptionName

        // setup validation
        nameField.doAfterTextChanged {
            if (it!!.isEmpty()) {
                newSubscriptionViewModel.inputNameState.value = InputState.EMPTY
//                nameFieldContainer.error =
//                    getString(R.string.new_subscription_field_error_empty_value)
                return@doAfterTextChanged
            }
            newSubscriptionViewModel.inputNameState.value = InputState.CORRECT
//            nameFieldContainer.error = ""
        }

        newSubscriptionViewModel.inputNameState.observe(requireActivity()) { state ->

            nameFieldContainer.error =
                if (state == InputState.EMPTY)
                    getString(R.string.new_subscription_field_error_empty_value)
                else
                    ""

        }

        // setup validation to enable "create" button
        //nameField.addTextChangedListener(buttonCreateActivationTextWatcher)

    }

    private fun setupCostInputField() {

        val costFieldContainer = binding.tilNewSubscriptionCost
        val costField = binding.tietNewSubscriptionCost

        costField.setText(getString(R.string.new_subscription_tiet_cost_default_value))
        newSubscriptionViewModel.inputCostState.value = InputState.CORRECT

        costField.doAfterTextChanged { value ->

            if (value!!.isEmpty()) {
                newSubscriptionViewModel.inputCostState.value = InputState.EMPTY
//                costFieldContainer.error =
//                    getString(R.string.new_subscription_field_error_empty_value)
                return@doAfterTextChanged
            }

            try {
                value.toString().toDouble()
            } catch (nfe: NumberFormatException) {
                newSubscriptionViewModel.inputCostState.value = InputState.WRONG
//                costFieldContainer.error =
//                    getString(R.string.new_subscription_field_error_wrong_value)
                return@doAfterTextChanged
            }

            newSubscriptionViewModel.inputCostState.value = InputState.CORRECT
            //costFieldContainer.error = ""

        }

        newSubscriptionViewModel.inputCostState.observe(requireActivity()) { state ->

            when (state) {
                InputState.EMPTY -> {
                    costFieldContainer.error = getString(R.string.new_subscription_field_error_empty_value)
                }
                InputState.WRONG -> {
                    costFieldContainer.error = getString(R.string.new_subscription_field_error_wrong_value)
                }
                else -> {
                    costFieldContainer.error = ""
                }
            }

        }

        // setup validation to enable "create" button
        //costField.addTextChangedListener(buttonCreateActivationTextWatcher)

    }

    private fun setupStartDateInputField() {

        val dateField = binding.tietNewSubscriptionStartDate

        // init DatePicker and restore selection
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(newSubscriptionViewModel.startDateSelectionLiveData.value)
            .setTitleText(getString(R.string.new_subscription_til_start_date_date_picker_title_text))
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    newSubscriptionViewModel.startDateSelectionLiveData.value = selection
                }
            }

        newSubscriptionViewModel.startDateSelectionLiveData.observe(requireActivity()) {
                selection -> updateDateFieldValue(selection)
        }

        dateField.setOnClickListener {
            datePicker.show(
                parentFragmentManager,
                getString(R.string.new_subscription_til_start_date_date_picker_tag)
            )
        }

    }

    private fun setupCreateButton() {

        val button = binding.btnNewSubscriptionCreate

        button.setOnClickListener{
            newSubscriptionViewModel.insertNewSubscription(
                parseSubscription()
            )
            findNavController().popBackStack()
        }

        newSubscriptionViewModel.buttonCreateEnabledLiveData.observe(
            requireActivity()) { enabled ->

            button.isEnabled = enabled
            button.setTextColor(
                if (enabled) ResourcesCompat.getColor(resources, R.color.green, null)
                else ResourcesCompat.getColor(resources, R.color.white_gray, null)
            )
        }

    }

    private fun setupCurrencyInputField() {

        val currencyFieldContainer = binding.tilNewSubscriptionCurrency
        val currencyField = binding.actvNewSubscriptionCurrency

        // restore value or set default
        if (newSubscriptionViewModel.inputCurrencySelection == "") {
            newSubscriptionViewModel.inputCurrencySelection =
                getString(R.string.new_subscription_tiet_currency_default_value)
            newSubscriptionViewModel.inputCurrencyState.value = InputState.CORRECT
        }
        currencyField.setText(newSubscriptionViewModel.inputCurrencySelection)

        // set menu items
        currencyField.setAdapter(ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            availableCurrencies
        ))

        // setup validation
        currencyField.doAfterTextChanged {

            val newValue = it.toString()

            // save value
            newSubscriptionViewModel.inputCurrencySelection = newValue

            if (newValue.isEmpty()) {
                newSubscriptionViewModel.inputCurrencyState.value = InputState.EMPTY
                return@doAfterTextChanged
            }

            // force only capital characters
            if (newValue != newValue.uppercase()) {
                currencyField.setText(newValue.uppercase())
                currencyField.setSelection(currencyField.length())
            }

            // show error on wrong value
            try {
                if (!availableCurrencies.contains(Currency.getInstance(newValue))) {
                    newSubscriptionViewModel.inputCurrencyState.value = InputState.WRONG
                    return@doAfterTextChanged
                }
            } catch (exception: IllegalArgumentException) {
                newSubscriptionViewModel.inputCurrencyState.value = InputState.WRONG
                return@doAfterTextChanged
            }

            newSubscriptionViewModel.inputCurrencyState.value = InputState.CORRECT

        }

        // setup state observe
        newSubscriptionViewModel.inputCurrencyState.observe(requireActivity()) { state ->

            when (state) {
                InputState.EMPTY -> {
                    currencyFieldContainer.error = getString(R.string.new_subscription_field_error_empty_value)
                }
                InputState.WRONG -> {
                    currencyFieldContainer.error = getString(R.string.new_subscription_field_error_wrong_value)
                }
                else -> {
                    currencyFieldContainer.error = ""
                }
            }

        }

    }

    private fun setupRenewalPeriodInputField() {

        val renewalPeriodField = binding.actvNewSubscriptionRenewalPeriod

        // restore value or set default
        if (newSubscriptionViewModel.inputRenewalPeriodSelection == "") {
            newSubscriptionViewModel.inputRenewalPeriodSelection =
                getString(R.string.new_subscription_actv_renewal_period_default_value)
        }
        renewalPeriodField.setText(newSubscriptionViewModel.inputRenewalPeriodSelection)

        // set menu items
        renewalPeriodField.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_renewal_period_options)
                .values
                .toTypedArray()
        ))

        // save value
        renewalPeriodField.doAfterTextChanged {
            newSubscriptionViewModel.inputRenewalPeriodSelection = it.toString()
        }


    }

    private fun setupAlertInputField() {

        val alertField = binding.actvNewSubscriptionAlert

        // restore value or set default
        if (newSubscriptionViewModel.inputAlertSelection == "") {
            newSubscriptionViewModel.inputAlertSelection =
                getString(R.string.new_subscription_actv_alert_default_value)
        }
        alertField.setText(newSubscriptionViewModel.inputAlertSelection)

        // set menu items
        alertField.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_alert_period_options)
                .values
                .toTypedArray()
        ))

        // save value
        alertField.doAfterTextChanged {
            newSubscriptionViewModel.inputAlertSelection = it.toString()
        }

    }

    private fun parseSubscription() : Subscription {

        val name = binding.tietNewSubscriptionName.text.toString()

        val description = binding.tietNewSubscriptionDescription.text.toString()

        val paymentCost =
            binding.tietNewSubscriptionCost.text.toString().toDouble().round(2)

        val paymentCurrency = Currency.getInstance(
            binding.actvNewSubscriptionCurrency.text.toString()
        )

        // start date
        val startDate = parseLocalDateFromUTCMilliseconds(
            //newSubscriptionViewModel.currentDateSelection
            newSubscriptionViewModel.startDateSelectionLiveData.value!!
        )

        // renewal period
        val renewalPeriodStr = binding.actvNewSubscriptionRenewalPeriod.text.toString()
        val renewalPeriodKey =
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_renewal_period_options)
                .filterValues{ it == renewalPeriodStr }
                .keys
                .toTypedArray()[0]
        val renewalPeriod = Period.parse(renewalPeriodKey)

        // alert flag and period
        val alertEnabled: Boolean
        val alertPeriod: Period
        val alertPeriodStr = binding.actvNewSubscriptionAlert.text.toString()
        val alertPeriodKey =
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_alert_period_options)
                .filterValues{ it == alertPeriodStr }
                .keys
                .toTypedArray()[0]
        if (alertPeriodKey == getString(R.string.new_subscription_alert_disabled_key)) {
            alertEnabled = false
            alertPeriod = Period.ZERO
        } else {
            alertEnabled = true
            alertPeriod = Period.parse(alertPeriodKey)
        }

        return Subscription(
            name = name,
            description = description,
            paymentCost = paymentCost,
            paymentCurrency = paymentCurrency,
            startDate = startDate,
            renewalPeriod = renewalPeriod,
            alertEnabled = alertEnabled,
            alertPeriod = alertPeriod
        )

    }

    private fun updateDateFieldValue(dateInUTCMillis: Long) {

        val formatter = DateTimeFormatter.ofPattern(
            getString(R.string.new_subscription_tiet_start_date_pattern)
        )

        val formattedString = parseLocalDateFromUTCMilliseconds(
            millis = dateInUTCMillis
        ).format(formatter)

        binding.tietNewSubscriptionStartDate.setText(formattedString)

    }

    private fun startValidationObserve() {

        val inputNameState = newSubscriptionViewModel.inputNameState
        val inputCostState = newSubscriptionViewModel.inputCostState
        val inputCurrencyState = newSubscriptionViewModel.inputCurrencyState
        val buttonCreateState = newSubscriptionViewModel.buttonCreateEnabledLiveData

        val observer = Observer<InputState> {
            buttonCreateState.value = (
                    inputNameState.value == InputState.CORRECT
                            && inputCostState.value == InputState.CORRECT
                            && inputCurrencyState.value == InputState.CORRECT
                    )
        }

        inputNameState.observe(requireActivity(), observer)
        inputCostState.observe(requireActivity(), observer)
        inputCurrencyState.observe(requireActivity(), observer)

    }

}