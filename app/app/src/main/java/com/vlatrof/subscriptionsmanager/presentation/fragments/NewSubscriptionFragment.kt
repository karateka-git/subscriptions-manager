package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
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
        setupCostInput()
        setupStartDateInput()
        setupAlertsInput()
        setupCreateButton()
    }

    override fun onResume() {
        super.onResume()
        setupCurrencyInput()
        setupRenewalPeriodInput()
    }

    private fun setupGoBackButton() {
        binding.btnNewSubscriptionGoBack.setOnClickListener{
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun setupCostInput() {

        val costField = binding.tietNewSubscriptionCost

        // setup default value
        costField.setText("0.00")

        // setup default value if lose focus with no entered value
        costField.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && costField.text!!.isEmpty()) {
                binding.tietNewSubscriptionCost.setText("0.00")
            }
        }

    }

    private fun setupStartDateInput() {

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

    private fun setupAlertsInput() {

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

    private fun setupCurrencyInput() {

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

        // setup input validation after lose focus
        currencyField.setOnFocusChangeListener { v, hasFocus ->

            // skip if get focus
            if (hasFocus) return@setOnFocusChangeListener

            // show error if entered currency is empty
            if (currencyField.text.toString().isEmpty()) {
                currencyFieldContainer.error =
                    getString(R.string.til_new_subscription_currency_error_empty_currency)
                return@setOnFocusChangeListener
            }

            // show error if entered currency code don't available
            try {
                if (!availableCurrencies.contains(
                        Currency.getInstance(currencyField.text.toString()))
                ) {
                    currencyFieldContainer.error =
                        getString(R.string.til_new_subscription_currency_error_wrong_currency)
                    return@setOnFocusChangeListener
                }
            } catch (exception: IllegalArgumentException) {
                currencyFieldContainer.error =
                    getString(R.string.til_new_subscription_currency_error_wrong_currency)
                return@setOnFocusChangeListener
            }

            // else clear error
            currencyFieldContainer.error = ""

        }

    }

    private fun setupRenewalPeriodInput() {

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