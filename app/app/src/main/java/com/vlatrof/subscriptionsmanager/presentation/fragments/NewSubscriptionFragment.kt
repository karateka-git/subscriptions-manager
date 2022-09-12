package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import java.util.Currency
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentNewSubscriptionBinding

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private lateinit var binding: FragmentNewSubscriptionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewSubscriptionBinding.bind(view)
        initCurrenciesSpinner()
    }

    private fun initCurrenciesSpinner() {

        val currencies = Currency.getAvailableCurrencies().toTypedArray()

        val adapter = ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_item,
            currencies
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.spinnerCurrency.adapter = adapter

        binding.spinnerCurrency.setSelection(
            currencies.indexOf(Currency.getInstance("USD"))
        )

    }

}