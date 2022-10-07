package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentNewSubscriptionBinding
import com.vlatrof.subscriptionsmanager.presentation.utils.hideKeyboard
import java.time.Period
import java.util.Currency

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private lateinit var binding: FragmentNewSubscriptionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewSubscriptionBinding.bind(view)
        setupGoBackButton()
        setupStartDateInput()
        setupAlertsInput()
    }

    override fun onResume() {
        super.onResume()
        setupCurrencyInput()
        setupRenewalPeriodInput()
    }

    private fun setupGoBackButton() {
        binding.btnGoBack.setOnClickListener{
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun setupStartDateInput() {
        binding.startDatePicker.setOnClickListener{

            val datePicker = MaterialDatePicker
                .Builder
                .datePicker()
                .setTitleText("Select start date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener {
                binding.startDatePicker.setText(datePicker.headerText)
            }

            datePicker.show(parentFragmentManager, "datePicker")

        }
    }

    private fun setupCurrencyInput() {

        val availableCurrencies = Currency.getAvailableCurrencies().toTypedArray()

        val currenciesAdapter = ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availableCurrencies
        )

        binding.currenciesSpinner.setAdapter(currenciesAdapter)

    }

    private fun setupRenewalPeriodInput() {

        val availablePeriods = arrayListOf<Period>(
            Period.ofMonths(1),
            Period.ofMonths(2)
        )

        val periodsAdapter = ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            availablePeriods
        )

        binding.renewalPeriodsSpinner.setAdapter(periodsAdapter)

    }

    private fun setupAlertsInput() {

        val availableAlerts = arrayListOf(
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

        binding.alertsSpinner.setAdapter(alertsAdapter)

        // set default selection to "none"
        val defaultValue = "None"
        binding.alertsSpinner.setText(defaultValue, false)

    }

}