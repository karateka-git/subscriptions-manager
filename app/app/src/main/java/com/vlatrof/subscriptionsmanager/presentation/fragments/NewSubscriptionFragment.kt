package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
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
        setupAlertsInputField()
        setupCreateButton()
    }

    override fun onResume() {
        super.onResume()
        setupCurrencyInputField()
        setupRenewalPeriodInputField()
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

        val costField = binding.tietNewSubscriptionCost
        val defaultValue = getString(R.string.new_subscription_tiet_cost_default_value)

        // setup default value on screen start
        costField.setText(defaultValue)

        // setup default value if field is empty
        costField.doAfterTextChanged {

            if (it!!.isEmpty()){
                costField.setText(defaultValue)
                costField.setSelection(defaultValue.length)
            }

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

    private fun setupAlertsInputField() {

        val alertsField = binding.actvNewSubscriptionAlerts

        val availableAlerts = arrayOf(
            "None",
            "Same day (12:00 PM)",
            "One day before (12:00 PM)",
            "Two days before (12:00 PM)",
            "One week before (12:00 PM)",
        )

        val alertsAdapter = ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availableAlerts
        )

        alertsField.setAdapter(alertsAdapter)

        // setup default selection to "none"
        val defaultValue = "None"
        alertsField.setText(defaultValue, false)

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

        val availablePeriods = arrayOf(
            "Daily",
            "Weekly",
            "Every 2 weeks",
            "Monthly",
            "Every 3 months",
            "Every 6 months",
            "Yearly"
        )

        val periodsAdapter = ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availablePeriods
        )

        renewalPeriodField.setAdapter(periodsAdapter)

        // set default selection to "monthly"
        val defaultValue = "Monthly"
        renewalPeriodField.setText(defaultValue, false)

    }

}