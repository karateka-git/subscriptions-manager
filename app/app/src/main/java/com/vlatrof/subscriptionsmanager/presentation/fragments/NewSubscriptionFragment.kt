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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private lateinit var binding: FragmentNewSubscriptionBinding
    private lateinit var datePicker: MaterialDatePicker<Long>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewSubscriptionBinding.bind(view)
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
                nameFieldContainer.error = getString(R.string.new_subscription_field_error_required_empty_string)
                return@doAfterTextChanged
            }

            nameFieldContainer.error = ""

        }

    }

    private fun setupCostInputField() {

        val costFieldContainer = binding.tilNewSubscriptionCost
        val costField = binding.tietNewSubscriptionCost

        // setup default value on screen start
        costField.setText(getString(R.string.new_subscription_tiet_cost_default_value))

        // show error if field is empty
        costField.doAfterTextChanged {

            if (it!!.isEmpty()) {
                costFieldContainer.error = getString(R.string.new_subscription_field_error_required_empty_string)
                return@doAfterTextChanged
            }

            costFieldContainer.error = ""

        }

    }

    private fun setupStartDateInputField() {

        val dateField = binding.tietNewSubscriptionStartDate

        // init date picker instance
        datePicker = MaterialDatePicker
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

    private fun setupAlertInputField() {

        val alertField = binding.actvNewSubscriptionAlert

        val availableValues = resources.getStringArray(R.array.alert_options)

        alertField.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availableValues
        ))

        val defaultValue = getString(R.string.new_subscription_tiet_alert_default_value)
        val defaultSelection =
            if (availableValues.contains(defaultValue)) defaultValue else availableValues[0]

        alertField.setText(defaultSelection, false)

    }

    private fun setupCreateButton() {

        binding.btnNewSubscriptionSubmit.setOnClickListener{

            // todo

        }
    }

    private fun setupCurrencyInputField() {

        val currencyFieldContainer = binding.tilNewSubscriptionCurrency
        val currencyField = binding.actvNewSubscriptionCurrency

        // setup fill currencies spinner with values
        val availableCurrencies = Currency.getAvailableCurrencies().toTypedArray()

        val currenciesAdapter = ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availableCurrencies
        )
        currencyField.setAdapter(currenciesAdapter)

        // setup default selection to "USD"
        val defaultValue = "USD"
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
                currencyFieldContainer.error = getString(R.string.new_subscription_field_error_required_empty_string)
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

    }

    private fun setupRenewalPeriodInputField() {

        val renewalPeriodField = binding.actvNewSubscriptionRenewalPeriod

        val availableValues = resources.getStringArray(R.array.renewal_period_options)

        renewalPeriodField.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availableValues
        ))

        val defaultValue = getString(R.string.new_subscription_tiet_renewal_period_default_value)
        val defaultSelection =
            if (availableValues.contains(defaultValue)) defaultValue else availableValues[0]

        renewalPeriodField.setText(defaultSelection, false)

    }

}