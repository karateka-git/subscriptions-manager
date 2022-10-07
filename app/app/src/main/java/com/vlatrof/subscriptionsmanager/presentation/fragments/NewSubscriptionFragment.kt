package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentNewSubscriptionBinding
import com.vlatrof.subscriptionsmanager.presentation.utils.hideKeyboard
import java.time.LocalDate
import java.time.Period
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
        binding.btnGoBack.setOnClickListener{
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun setupCostInput() {
        binding.tietNewSubscriptionCost.setText("0.00")
    }

    private fun setupStartDateInput() {

        // init date picker instance
        datePicker = MaterialDatePicker
            .Builder
            .datePicker()
            .setTitleText("Select start date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            binding.tietNewSubscriptionStartDate.setText(datePicker.headerText)
        }

        binding.tietNewSubscriptionStartDate.setOnClickListener{
            datePicker.show(parentFragmentManager, "datePicker")
        }

        // set current date as default value
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
        val formattedString = LocalDate.now().format(formatter)
        binding.tietNewSubscriptionStartDate.setText(formattedString)

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

    private fun setupCreateButton() {
        binding.btnCreateNewSubscription.setOnClickListener{

            val allFilled = false

            // check are all required inputs filled

            if(!allFilled) {
                Toast.makeText(requireActivity(),
                    "Not all inputs filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(requireActivity(),
                "fdafdasfdsfsadfas", Toast.LENGTH_SHORT).show()

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

        // set default selection to "USD"
        val defaultValue = "USD"
        binding.currenciesSpinner.setText(defaultValue, false)

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

        // set default selection to "monthly"
        val defaultValue = availablePeriods[0].toString()
        binding.renewalPeriodsSpinner.setText(defaultValue, false)

    }

}