package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import java.util.Currency
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentNewSubscriptionBinding
import java.time.Period

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private lateinit var binding: FragmentNewSubscriptionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewSubscriptionBinding.bind(view)
        setupCurrenciesSpinner()
        setupRenewalPeriodInput()
    }

    private fun setupCurrenciesSpinner() {

        val currencies = Currency.getAvailableCurrencies().toTypedArray()

        binding.spinnerCurrency.adapter = ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_item,
            currencies
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.spinnerCurrency.setSelection(
            currencies.indexOf(Currency.getInstance("USD"))
        )

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
        
        binding.renewalPeriodSpinner.setAdapter(periodsAdapter)

    }

}