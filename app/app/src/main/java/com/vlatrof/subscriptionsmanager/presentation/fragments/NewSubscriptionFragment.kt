package com.vlatrof.subscriptionsmanager.presentation.fragments

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
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.BaseViewModel
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.NewSubscriptionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Currency

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    private val newSubscriptionViewModel by viewModel<NewSubscriptionViewModel>()
    private lateinit var binding: FragmentNewSubscriptionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewSubscriptionBinding.bind(view)
        setupGoBackButton()
        setupNameInput()
        setupCostInput()
        setupStartDateInput()
        setupCreateButton()
    }

    override fun onResume() {
        super.onResume()
        setupCurrencyInput()
        setupRenewalPeriodInput()
        setupAlertPeriodInput()
    }

    private fun setupGoBackButton() {

        binding.btnNewSubscriptionGoBack.setOnClickListener {
            hideKeyboard()
            findNavController().popBackStack()
        }

    }

    private fun setupNameInput() {

        // validate new value
        binding.tietNewSubscriptionName.doAfterTextChanged {
            newSubscriptionViewModel.validateNameInput(it.toString())
            newSubscriptionViewModel.updateCreateButtonState()
        }

        // handle new state
        newSubscriptionViewModel.nameInputState.observe(viewLifecycleOwner) {
            binding.tilNewSubscriptionName.error = getString(it.errorStringResourceId)
        }

    }

    private fun setupCostInput() {

        // set initial value
        val initialValue = "0.0"
        binding.tietNewSubscriptionCost.setText(initialValue)
        newSubscriptionViewModel.validateCostInput(initialValue)

        // validate new value
        binding.tietNewSubscriptionCost.doAfterTextChanged {
            newSubscriptionViewModel.validateCostInput(it.toString())
            newSubscriptionViewModel.updateCreateButtonState()
        }

        // handle new state
        newSubscriptionViewModel.costInputState.observe(viewLifecycleOwner) {
            binding.tilNewSubscriptionCost.error = getString(it.errorStringResourceId)
        }

    }

    private fun setupStartDateInput() {

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
                Parser.parseLocalDateFromUTCMilliseconds(newSelection)
                    .format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
            )
        }

    }

    private fun setupCreateButton() {

        // handle click
        binding.btnNewSubscriptionCreate.setOnClickListener {
            val subscriptionToCreate = parseSubscription()
            newSubscriptionViewModel.insertNewSubscription(subscriptionToCreate)
            newSubscriptionViewModel.saveLastCurrencyCode(
                subscriptionToCreate.paymentCurrency.currencyCode
            )
            hideKeyboard()
            findNavController().popBackStack()
        }

        // handle new state
        newSubscriptionViewModel.buttonCreateState.observe(viewLifecycleOwner) { enabled ->
            binding.btnNewSubscriptionCreate.apply {
                isEnabled = enabled
                setTextColor(
                    if (enabled) ResourcesCompat.getColor(resources, R.color.green, null)
                    else ResourcesCompat.getColor(resources, R.color.gray, null)
                )
            }
        }

    }

    private fun setupCurrencyInput() {

        val currencyField = binding.actvNewSubscriptionCurrency

        // set menu items
        currencyField.setAdapter(ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            Currency.getAvailableCurrencies().toTypedArray()
        ))

        // if initial input state: restore last value or set default
        if (newSubscriptionViewModel.currencyInputState.value == BaseViewModel.InputState.INITIAL) {
            val defaultCurrencyCode = newSubscriptionViewModel.getLastCurrencyCode()
            newSubscriptionViewModel.currencyInputSelection = defaultCurrencyCode
            newSubscriptionViewModel.validateCurrencyInput(defaultCurrencyCode)
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
            newSubscriptionViewModel.updateCreateButtonState()

        }

        // handle new input state
        newSubscriptionViewModel.currencyInputState.observe(viewLifecycleOwner) { newState ->
            binding.tilNewSubscriptionCurrency.error = getString(newState.errorStringResourceId)
        }

    }

    private fun setupRenewalPeriodInput() {

        val renewalPeriodField = binding.actvNewSubscriptionRenewalPeriod
        val optionsHolder = RenewalPeriodOptionsHolder(resources)

        // set menu items
        renewalPeriodField.setAdapter(ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            optionsHolder.options.values.toTypedArray()
        ))

        // restore value or set default
        if (newSubscriptionViewModel.renewalPeriodInputSelection.isEmpty()) {
            newSubscriptionViewModel.renewalPeriodInputSelection = optionsHolder.defaultValue
        }
        renewalPeriodField.setText(newSubscriptionViewModel.renewalPeriodInputSelection, false)

        // handle new value
        renewalPeriodField.doAfterTextChanged {
            newSubscriptionViewModel.renewalPeriodInputSelection = it.toString()
        }


    }

    private fun setupAlertPeriodInput() {

        val alertField = binding.actvNewSubscriptionAlert
        val optionsHolder = AlertPeriodOptionsHolder(resources)

        // set menu items
        alertField.setAdapter(ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            optionsHolder.options.values.toTypedArray()
        ))

        // restore value or set default
        if (newSubscriptionViewModel.alertInputSelection == "") {
            newSubscriptionViewModel.alertInputSelection = optionsHolder.defaultValue
        }
        alertField.setText(newSubscriptionViewModel.alertInputSelection, false)

        // handle new value
        alertField.doAfterTextChanged {
            newSubscriptionViewModel.alertInputSelection = it.toString()
        }

    }

    private fun parseSubscription() : Subscription {

        val name = binding.tietNewSubscriptionName.text.toString().trim()

        val description = binding.tietNewSubscriptionDescription.text.toString().trim()

        val paymentCost =
            binding.tietNewSubscriptionCost.text.toString().toDouble().round(2)

        val paymentCurrency = Currency.getInstance(
            binding.actvNewSubscriptionCurrency.text.toString()
        )

        // start date
        val startDate = Parser.parseLocalDateFromUTCMilliseconds(
            newSubscriptionViewModel.startDateInputSelection.value!!
        )

        // renewal period
        val renewalPeriod = Period.parse(RenewalPeriodOptionsHolder(resources).options
            .getFirstKey(binding.actvNewSubscriptionRenewalPeriod.text.toString())
        )

        // alert period
        val alertEnabled: Boolean
        val alertPeriod: Period
        val alertPeriodOptionsHolder = AlertPeriodOptionsHolder(resources)
        val alertPeriodValue = binding.actvNewSubscriptionAlert.text.toString()
        if (alertPeriodValue == alertPeriodOptionsHolder.defaultValue) {
            alertEnabled = false
            alertPeriod = Period.ZERO
        } else {
            alertEnabled = true
            alertPeriod = Period.parse(
                alertPeriodOptionsHolder.options.getFirstKey(alertPeriodValue)
            )
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