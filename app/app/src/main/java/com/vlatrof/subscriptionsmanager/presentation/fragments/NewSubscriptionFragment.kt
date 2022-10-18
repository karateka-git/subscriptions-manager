package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentNewSubscriptionBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.utils.*
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.InputState
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.NewSubscriptionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Currency

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private lateinit var binding: FragmentNewSubscriptionBinding
    private val newSubscriptionViewModel by viewModel<NewSubscriptionViewModel>()

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

        binding.btnNewSubscriptionGoBack.setOnClickListener {
            hideKeyboard()
            findNavController().popBackStack()
        }

    }

    private fun setupNameInputField() {

        // validate new value
        binding.tietNewSubscriptionName.doAfterTextChanged {
            newSubscriptionViewModel.validateNameInput(it.toString())
            newSubscriptionViewModel.validateCreateButton()
        }

        // handle new state
        newSubscriptionViewModel.nameInputState.observe(viewLifecycleOwner) {
            binding.tilNewSubscriptionName.error = getString(it.stringResourceId)
        }

    }

    private fun setupCostInputField() {

        // set initial value
        getString(R.string.subscription_e_f_tiet_cost_initial_value).let {
            binding.tietNewSubscriptionCost.setText(it)
            newSubscriptionViewModel.validateCostInput(it)
        }

        // validate new value
        binding.tietNewSubscriptionCost.doAfterTextChanged {
            newSubscriptionViewModel.validateCostInput(it.toString())
            newSubscriptionViewModel.validateCreateButton()
        }

        // handle new state
        newSubscriptionViewModel.costInputState.observe(viewLifecycleOwner) {
            binding.tilNewSubscriptionCost.error = getString(it.stringResourceId)
        }

    }

    private fun setupStartDateInputField() {

        val dateField = binding.tietNewSubscriptionStartDate

        // init DatePicker and restore selection
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(newSubscriptionViewModel.startDateInputSelection.value)
            .setTitleText(getString(R.string.subscription_e_f_til_start_date_date_picker_title_text))
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    newSubscriptionViewModel.startDateInputSelection.value = selection
                }
            }

        // handle input click
        dateField.setOnClickListener {
            datePicker.show(
                parentFragmentManager,
                getString(R.string.subscription_e_f_til_start_date_date_picker_tag)
            )
        }

        // handle new date selection
        newSubscriptionViewModel.startDateInputSelection.observe(viewLifecycleOwner) { newSelection ->
            binding.tietNewSubscriptionStartDate.setText(
                parseLocalDateFromUTCMilliseconds(newSelection)
                    .format(DateTimeFormatter.ofPattern(
                        getString(R.string.subscription_e_f_tiet_start_date_pattern)
                    ))
            )
        }

    }

    private fun setupCreateButton() {

        // handle click
        binding.btnNewSubscriptionCreate.setOnClickListener{
            newSubscriptionViewModel.insertNewSubscription(parseSubscription())
            hideKeyboard()
            findNavController().popBackStack()
        }

        // handle new state
        newSubscriptionViewModel.buttonCreateState.observe(viewLifecycleOwner) { enabled ->
            binding.btnNewSubscriptionCreate.apply {
                isEnabled = enabled
                setTextColor(
                    if (enabled) ResourcesCompat.getColor(resources, R.color.green, null)
                    else ResourcesCompat.getColor(resources, R.color.white_gray, null)
                )
            }
        }

    }

    private fun setupCurrencyInputField() {

        val currencyField = binding.actvNewSubscriptionCurrency

        // set menu items
        currencyField.setAdapter(ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            Currency.getAvailableCurrencies().toTypedArray()
        ))

        // restore value or set initial
        if (newSubscriptionViewModel.currencyInputState.value == InputState.INITIAL) {
            getString(R.string.subscription_e_f_tiet_currency_initial_value).let{
                newSubscriptionViewModel.currencyInputSelection = it
                newSubscriptionViewModel.validateCurrencyInput(it)
            }
        }
        currencyField.setText(newSubscriptionViewModel.currencyInputSelection)

        // handle new value
        currencyField.doAfterTextChanged {

            var newValue = it.toString()

            // force only capital characters
            if (newValue.isNotEmpty() && newValue != newValue.uppercase()) {
                newValue = newValue.uppercase()
                currencyField.setText(newValue)
                currencyField.setSelection(newValue.length)
            }

            newSubscriptionViewModel.currencyInputSelection = newValue
            newSubscriptionViewModel.validateCurrencyInput(newValue)
            newSubscriptionViewModel.validateCreateButton()

        }

        // handle new input state
        newSubscriptionViewModel.currencyInputState.observe(viewLifecycleOwner) { newState ->
            binding.tilNewSubscriptionCurrency.error = getString(newState.stringResourceId)
        }

    }

    private fun setupRenewalPeriodInputField() {

        val renewalPeriodField = binding.actvNewSubscriptionRenewalPeriod

        // set menu items
        renewalPeriodField.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_renewal_period_options)
                .values
                .toTypedArray()
        ))

        // restore value or set initial
        if (newSubscriptionViewModel.renewalPeriodInputSelection == "") {
            newSubscriptionViewModel.renewalPeriodInputSelection =
                getString(R.string.subscription_e_f_actv_renewal_period_initial_value)
        }
        renewalPeriodField.setText(newSubscriptionViewModel.renewalPeriodInputSelection, false)

        // handle new value
        renewalPeriodField.doAfterTextChanged { newValue ->
            newSubscriptionViewModel.renewalPeriodInputSelection = newValue.toString()
        }


    }

    private fun setupAlertInputField() {

        val alertField = binding.actvNewSubscriptionAlert

        // set menu items
        alertField.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_alert_period_options)
                .values
                .toTypedArray()
        ))

        // restore value or set default
        if (newSubscriptionViewModel.alertInputSelection == "") {
            newSubscriptionViewModel.alertInputSelection =
                getString(R.string.subscription_e_f_actv_alert_default_value)
        }
        alertField.setText(newSubscriptionViewModel.alertInputSelection, false)

        // handle new value
        alertField.doAfterTextChanged { newValue ->
            newSubscriptionViewModel.alertInputSelection = newValue.toString()
        }

    }

    private fun parseSubscription() : Subscription {

        val name = binding.tietNewSubscriptionName.text.toString()

        val description = binding.tietNewSubscriptionDescription.text.toString()

        val paymentCost =
            binding.tietNewSubscriptionCost.text.toString().toDouble().round(2)

        val paymentCurrency = Currency.getInstance(
            binding.actvNewSubscriptionCurrency.text.toString()
        )

        // start date
        val startDate = parseLocalDateFromUTCMilliseconds(
            newSubscriptionViewModel.startDateInputSelection.value!!
        )

        // renewal period
        val renewalPeriodStr = binding.actvNewSubscriptionRenewalPeriod.text.toString()
        val renewalPeriodKey =
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_renewal_period_options)
                .filterValues{ it == renewalPeriodStr }
                .keys
                .toTypedArray()[0]
        val renewalPeriod = Period.parse(renewalPeriodKey)

        // alert flag and period
        val alertEnabled: Boolean
        val alertPeriod: Period
        val alertPeriodStr = binding.actvNewSubscriptionAlert.text.toString()
        val alertPeriodKey =
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_alert_period_options)
                .filterValues{ it == alertPeriodStr }
                .keys
                .toTypedArray()[0]
        if (alertPeriodKey == getString(R.string.subscription_e_f_alert_disabled_key)) {
            alertEnabled = false
            alertPeriod = Period.ZERO
        } else {
            alertEnabled = true
            alertPeriod = Period.parse(alertPeriodKey)
        }

        return Subscription(
            name = name,
            description = description,
            paymentCost = paymentCost,
            paymentCurrency = paymentCurrency,
            startDate = startDate,
            renewalPeriod = renewalPeriod,
            alertEnabled = alertEnabled,
            alertPeriod = alertPeriod
        )

    }

}