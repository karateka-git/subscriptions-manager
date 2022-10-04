package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentNewSubscriptionBinding
import java.time.Period
import java.util.Currency
import java.util.Calendar

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private lateinit var binding: FragmentNewSubscriptionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewSubscriptionBinding.bind(view)
        setupGoBackButton()
        setupStartDatePicker()
    }

    override fun onResume() {
        super.onResume()
        setupCurrenciesSpinner()
        setupRenewalPeriodInput()
    }

    private fun setupGoBackButton() {
        binding.btnGoBack.setOnClickListener{ findNavController().popBackStack() }
    }

    private fun setupStartDatePicker() {
        binding.startDatePicker.setOnClickListener{
            val datePicker = DatePickerFragment(binding.startDatePicker)
            datePicker.show(parentFragmentManager, "datePicker")
        }
    }

    private fun setupCurrenciesSpinner() {

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

    class DatePickerFragment(

        private val input: TextInputEditText

        ) : DialogFragment(), DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // Create a new instance of DatePickerDialog and return it
            return DatePickerDialog(requireContext(), this, year, month, day)

        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            val newDateStr = "${day}/${month}/${year}"
            input.setText(newDateStr)
        }

    }

}