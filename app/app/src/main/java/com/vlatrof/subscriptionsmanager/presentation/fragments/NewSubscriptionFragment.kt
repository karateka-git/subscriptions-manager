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
import com.vlatrof.subscriptionsmanager.presentation.utils.hideKeyboard
import java.lang.NumberFormatException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private lateinit var binding: FragmentNewSubscriptionBinding
    private lateinit var buttonCreateActivationTextWatcher: ButtonCreateActivationTextWatcher

    inner class ButtonCreateActivationTextWatcher : TextWatcher {

        private val nameInputField = binding.tietNewSubscriptionName
        private val costInputField = binding.tietNewSubscriptionCost
        private val currencyInputContainer = binding.tilNewSubscriptionCurrency
        private val buttonCreate = binding.btnNewSubscriptionCreate

        override fun afterTextChanged(s: Editable?) {

            buttonCreate.isEnabled = (nameInputField.text!!.isNotEmpty()
                    && costInputField.error.isNullOrEmpty()
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
        binding.btnNewSubscriptionGoBack.setOnClickListener{
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun setupNameInputField() {

        val nameFieldContainer = binding.tilNewSubscriptionName
        val nameField = binding.tietNewSubscriptionName

        nameField.doAfterTextChanged {

            if (it!!.isEmpty()) {
                nameFieldContainer.error = getString(R.string.new_subscription_field_error_empty_value)
                return@doAfterTextChanged
            }

            nameFieldContainer.error = ""

        }

        nameField.addTextChangedListener(buttonCreateActivationTextWatcher)

    }

    private fun setupCostInputField() {

        val costFieldContainer = binding.tilNewSubscriptionCost
        val costField = binding.tietNewSubscriptionCost

        costField.setText(getString(R.string.new_subscription_tiet_cost_default_value))

        costField.doAfterTextChanged { value ->

            if (value!!.isEmpty()) {
                costFieldContainer.error = getString(R.string.new_subscription_field_error_empty_value)
                return@doAfterTextChanged
            }

            try {
                value.toString().toDouble()
            } catch (nfe: NumberFormatException) {
                costFieldContainer.error = getString(R.string.new_subscription_field_error_wrong_value)
                return@doAfterTextChanged
            }

            costFieldContainer.error = ""

        }

        costField.addTextChangedListener(buttonCreateActivationTextWatcher)

    }

    private fun setupStartDateInputField() {

        val dateField = binding.tietNewSubscriptionStartDate

        val datePicker = MaterialDatePicker
            .Builder
            .datePicker()
            .setTitleText("Select start date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            dateField.setText(datePicker.headerText)
        }

        dateField.setOnClickListener{
            datePicker.show(parentFragmentManager, "datePicker")
        }

        // setup current date as default value
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
        val formattedString = LocalDate.now().format(formatter)
        dateField.setText(formattedString)

    }

    private fun setupCreateButton() {

        val button = binding.btnNewSubscriptionCreate

        button.isEnabled = false

    }

    private fun setupCurrencyInputField() {

        val currencyFieldContainer = binding.tilNewSubscriptionCurrency
        val currencyField = binding.actvNewSubscriptionCurrency

        val availableCurrencies = Currency.getAvailableCurrencies().toTypedArray()

        currencyField.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availableCurrencies
        ))

        // setup default selection to "USD"
        val defaultValue = getString(R.string.new_subscription_tiet_currency_default_value)
        currencyField.setText(defaultValue, false)

        // setup force only capital characters
        currencyField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(et: Editable) {
                var s = et.toString()
                if (s != s.uppercase(Locale.getDefault())) {
                    s = s.uppercase(Locale.getDefault())
                    currencyField.setText(s)
                    currencyField.setSelection(currencyField.length())
                }
            }
        })

        // setup input validation
        currencyField.doAfterTextChanged {

            val newValue = it.toString()

            if (newValue.isEmpty()) {
                currencyFieldContainer.error = getString(R.string.new_subscription_field_error_empty_value)
                return@doAfterTextChanged
            }

            try {

                val validCurrency = availableCurrencies.contains(Currency.getInstance(newValue))

                if (validCurrency) {
                    currencyFieldContainer.error = ""
                } else {
                    currencyFieldContainer.error = getString(R.string.new_subscription_field_error_wrong_value)
                }

            } catch (exception: IllegalArgumentException) {
                currencyFieldContainer.error = getString(R.string.new_subscription_field_error_wrong_value)
            }

        }

        currencyField.addTextChangedListener(buttonCreateActivationTextWatcher)

    }

    private fun setupRenewalPeriodInputField() {

        val renewalPeriodField = binding.actvNewSubscriptionRenewalPeriod

        val availableValues = resources.getStringArray(R.array.renewal_period_options)

        renewalPeriodField.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availableValues
        ))

        val defaultValue = getString(R.string.new_subscription_actv_renewal_period_default_value)
        val defaultSelection =
            if (availableValues.contains(defaultValue)) defaultValue else availableValues[0]

        renewalPeriodField.setText(defaultSelection, false)

    }

    private fun setupAlertInputField() {

        val alertField = binding.actvNewSubscriptionAlert

        val availableValues = resources.getStringArray(R.array.alert_options)

        alertField.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availableValues
        ))

        val defaultValue = getString(R.string.new_subscription_actv_alert_default_value)
        val defaultSelection =
            if (availableValues.contains(defaultValue)) defaultValue else availableValues[0]

        alertField.setText(defaultSelection, false)

    }

}