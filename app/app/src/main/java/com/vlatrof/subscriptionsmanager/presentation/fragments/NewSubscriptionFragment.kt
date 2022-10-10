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
import java.util.Currency

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

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

        // configure date picker
        val datePicker = MaterialDatePicker
            .Builder
            .datePicker()
            .setTitleText("Select start date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            dateField.setText(datePicker.headerText)
        }

        dateField.setOnClickListener {
            datePicker.show(parentFragmentManager, "datePicker")
        }

        // set default value
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

        val availableValues = resources.getStringArray(R.array.renewal_period_options)

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

        val availableValues = resources.getStringArray(R.array.alert_options)

        // set menu items
        alertField.setAdapter(
            ArrayAdapter(
                activity as Context,
                android.R.layout.simple_spinner_dropdown_item,
                availableValues
            )
        )

    }

}