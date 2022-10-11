package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentNewSubscriptionBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.utils.hideKeyboard
import com.vlatrof.subscriptionsmanager.presentation.utils.parseXmlResourceMap
import com.vlatrof.subscriptionsmanager.presentation.utils.round
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.NewSubscriptionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.NumberFormatException
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Currency

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private val newSubscriptionViewModel by viewModel<NewSubscriptionViewModel>()
    private lateinit var binding: FragmentNewSubscriptionBinding
    private lateinit var buttonCreateActivationTextWatcher: ButtonCreateActivationTextWatcher

    inner class ButtonCreateActivationTextWatcher : TextWatcher {

        private val nameInputField = binding.tietNewSubscriptionName
        private val costInputContainer = binding.tilNewSubscriptionCost
        private val currencyInputContainer = binding.tilNewSubscriptionCurrency
        private val buttonCreate = binding.btnNewSubscriptionCreate

        // validation to enable button
        override fun afterTextChanged(s: Editable?) {
            buttonCreate.isEnabled = (nameInputField.text!!.isNotEmpty()
                    && costInputContainer.error.isNullOrEmpty()
                    && currencyInputContainer.error.isNullOrEmpty())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewSubscriptionBinding.bind(view)
        buttonCreateActivationTextWatcher = ButtonCreateActivationTextWatcher()
        setupGoBackButton()
        setupNameInputField()
        setupCostInputField()
        setupStartDateInputField()
        setupCreateButton()
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
                nameFieldContainer.error =
                    getString(R.string.new_subscription_field_error_empty_value)
                return@doAfterTextChanged
            }
            nameFieldContainer.error = ""
        }

        // setup validation to enable "create" button
        nameField.addTextChangedListener(buttonCreateActivationTextWatcher)

    }

    private fun setupCostInputField() {

        val costFieldContainer = binding.tilNewSubscriptionCost
        val costField = binding.tietNewSubscriptionCost

        // set default value
        costField.setText(getString(R.string.new_subscription_tiet_cost_default_value))

        // setup validation
        costField.doAfterTextChanged { value ->

            // show error on empty value
            if (value!!.isEmpty()) {
                costFieldContainer.error =
                    getString(R.string.new_subscription_field_error_empty_value)
                return@doAfterTextChanged
            }

            // show error on wrong value
            try {
                value.toString().toDouble()
            } catch (nfe: NumberFormatException) {
                costFieldContainer.error =
                    getString(R.string.new_subscription_field_error_wrong_value)
                return@doAfterTextChanged
            }

            // clear error
            costFieldContainer.error = ""

        }

        // setup validation to enable "create" button
        costField.addTextChangedListener(buttonCreateActivationTextWatcher)

    }

    private fun setupStartDateInputField() {

        val dateField = binding.tietNewSubscriptionStartDate

        // date picker builder
        val datePickerBuilder =
            MaterialDatePicker
            .Builder
            .datePicker()
            .setTitleText("Select start date")

        // restore date selection or set today
        if (newSubscriptionViewModel.currentSelectionDate == 0L) {
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            datePickerBuilder.setSelection(today)
            newSubscriptionViewModel.currentSelectionDate = today
        } else {
            datePickerBuilder.setSelection(newSubscriptionViewModel.currentSelectionDate)
        }

        val datePicker = datePickerBuilder.build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            newSubscriptionViewModel.currentSelectionDate = selection
            dateField.setText(datePicker.headerText)
        }

        dateField.setOnClickListener {
            datePicker.show(parentFragmentManager, "datePicker")
        }

        // set default value
        val pattern = getString(R.string.new_subscription_tiet_start_date_pattern)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
        val formattedString = LocalDate.now().format(formatter)
        dateField.setText(formattedString)

    }

    private fun setupCreateButton() {

        val button = binding.btnNewSubscriptionCreate

        button.setOnClickListener{
            val newSubscription = parseSubscription()
            newSubscriptionViewModel.insertNewSubscription(newSubscription)
            findNavController().popBackStack()
        }

    }

    private fun setupCurrencyInputField() {

        val currencyFieldContainer = binding.tilNewSubscriptionCurrency
        val currencyField = binding.actvNewSubscriptionCurrency

        val availableCurrencies = Currency.getAvailableCurrencies().toTypedArray()

        // set menu items
        currencyField.setAdapter(
            ArrayAdapter(
                activity as Context,
                android.R.layout.simple_spinner_dropdown_item,
                availableCurrencies
            )
        )

        // setup validation
        currencyField.doAfterTextChanged {

            val newValue = it.toString()

            // show error on empty value
            if (newValue.isEmpty()) {
                currencyFieldContainer.error =
                    getString(R.string.new_subscription_field_error_empty_value)
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
                    currencyFieldContainer.error =
                        getString(R.string.new_subscription_field_error_wrong_value)
                    return@doAfterTextChanged
                }
            } catch (exception: IllegalArgumentException) {
                currencyFieldContainer.error =
                    getString(R.string.new_subscription_field_error_wrong_value)
                return@doAfterTextChanged
            }

            // clear error
            currencyFieldContainer.error = ""

        }

        // setup validation to enable "create" button
        currencyField.addTextChangedListener(buttonCreateActivationTextWatcher)

    }

    private fun setupRenewalPeriodInputField() {

        val renewalPeriodField = binding.actvNewSubscriptionRenewalPeriod

        val availableValues =
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_renewal_period_options)
                .values
                .toTypedArray()

        // set menu items
        renewalPeriodField.setAdapter(
            ArrayAdapter(
                activity as Context,
                android.R.layout.simple_spinner_dropdown_item,
                availableValues
            )
        )

    }

    private fun setupAlertInputField() {

        val alertField = binding.actvNewSubscriptionAlert

        val availableValues =
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_alert_period_options)
                .values
                .toTypedArray()

        // set menu items
        alertField.setAdapter(
            ArrayAdapter(
                activity as Context,
                android.R.layout.simple_spinner_dropdown_item,
                availableValues
            )
        )

    }

    private fun parseSubscription() : Subscription {

        // name
        val name = binding.tietNewSubscriptionName.text.toString()

        // description
        val description = binding.tietNewSubscriptionDescription.text.toString()

        // payment cost
        val paymentCost =
            binding.tietNewSubscriptionCost.text.toString().toDouble().round(2)

        // payment currency
        val paymentCurrencyCodeStr = binding.actvNewSubscriptionCurrency.text.toString()
        val paymentCurrency = Currency.getInstance(paymentCurrencyCodeStr)

        // start date

        val startDateStr = binding.tietNewSubscriptionStartDate.text.toString()
        val startDatePattern = getString(R.string.new_subscription_tiet_start_date_pattern)
        val dtf = DateTimeFormatter.ofPattern(startDatePattern)
        val startDate = LocalDate.parse(startDateStr, dtf)

        // renewal period
        val renewalPeriodStr = binding.actvNewSubscriptionRenewalPeriod.text.toString()
        val renewalPeriodKey =
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_renewal_period_options)
            .filterValues{ it == renewalPeriodStr }.keys.toTypedArray()[0]
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
}