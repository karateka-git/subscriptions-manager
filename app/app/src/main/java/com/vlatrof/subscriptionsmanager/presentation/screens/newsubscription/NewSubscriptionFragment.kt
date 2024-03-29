package com.vlatrof.subscriptionsmanager.presentation.screens.newsubscription

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.databinding.FragmentNewSubscriptionBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.screens.base.BaseViewModel
import com.vlatrof.subscriptionsmanager.utils.AlertPeriodOptions
import com.vlatrof.subscriptionsmanager.utils.Parser
import com.vlatrof.subscriptionsmanager.utils.RenewalPeriodOptions
import com.vlatrof.subscriptionsmanager.utils.getFirstKey
import com.vlatrof.subscriptionsmanager.utils.hideKeyboard
import com.vlatrof.subscriptionsmanager.utils.round
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Currency
import javax.inject.Inject

class NewSubscriptionFragment : Fragment(R.layout.fragment_new_subscription) {

    @Inject lateinit var newSubscriptionViewModelFactory: NewSubscriptionViewModelFactory
    private lateinit var newSubscriptionViewModel: NewSubscriptionViewModel
    private lateinit var binding: FragmentNewSubscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        createNewSubscriptionViewModel()
    }

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

    private fun injectDependencies() {
        (requireActivity().applicationContext as App).appComponent.inject(this)
    }

    private fun createNewSubscriptionViewModel() {
        newSubscriptionViewModel = ViewModelProvider(
            this,
            newSubscriptionViewModelFactory
        )[NewSubscriptionViewModel::class.java]
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
        newSubscriptionViewModel.nameInputState.observe(viewLifecycleOwner) { newState ->
            binding.tilNewSubscriptionName.error = getInputErrorStringByState(newState)
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
        newSubscriptionViewModel.costInputState.observe(viewLifecycleOwner) { newState ->
            binding.tilNewSubscriptionCost.error = getInputErrorStringByState(newState)
        }
    }

    private fun setupStartDateInput() {
        val dateField = binding.tietNewSubscriptionStartDate

        // init DatePicker and restore selection
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(newSubscriptionViewModel.startDateInputSelection.value)
            .setTitleText(
                getString(R.string.subscription_e_f_til_start_date_date_picker_title_text)
            )
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
        currencyField.setAdapter(
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                Currency.getAvailableCurrencies().toTypedArray()
            )
        )

        // if input state initial: restore last value or set default
        if (newSubscriptionViewModel.currencyInputState.value == BaseViewModel.InputState.Initial) {
            val defaultCurrencyCode = newSubscriptionViewModel.getLastCurrencyCode()
            newSubscriptionViewModel.currencyInputValue = defaultCurrencyCode
            newSubscriptionViewModel.validateCurrencyInput(defaultCurrencyCode)
        }
        currencyField.setText(newSubscriptionViewModel.currencyInputValue, false)

        // handle new value
        currencyField.doAfterTextChanged {
            var newValue = it.toString()

            // force only capital characters
            if (newValue.isNotEmpty() && newValue != newValue.uppercase()) {
                newValue = newValue.uppercase()
                currencyField.setText(newValue)
                currencyField.setSelection(newValue.length)
            }

            newSubscriptionViewModel.currencyInputValue = newValue
            newSubscriptionViewModel.validateCurrencyInput(newValue)
            newSubscriptionViewModel.updateCreateButtonState()
        }

        // handle new input state
        newSubscriptionViewModel.currencyInputState.observe(viewLifecycleOwner) { newState ->
            binding.tilNewSubscriptionCurrency.error = getInputErrorStringByState(newState)
        }
    }

    private fun setupRenewalPeriodInput() {
        val renewalPeriodField = binding.actvNewSubscriptionRenewalPeriod
        val renewalPeriodOptions = RenewalPeriodOptions(resources)

        // set menu items
        renewalPeriodField.setAdapter(
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                renewalPeriodOptions.options.values.toTypedArray()
            )
        )

        // restore value or set default
        if (newSubscriptionViewModel.renewalPeriodInputSelection.isEmpty()) {
            newSubscriptionViewModel.renewalPeriodInputSelection = renewalPeriodOptions.default
        }
        renewalPeriodField.setText(newSubscriptionViewModel.renewalPeriodInputSelection, false)

        // handle new value
        renewalPeriodField.doAfterTextChanged {
            newSubscriptionViewModel.renewalPeriodInputSelection = it.toString()
        }
    }

    private fun setupAlertPeriodInput() {
        val alertField = binding.actvNewSubscriptionAlert
        val optionsHolder = AlertPeriodOptions(resources)

        // set menu items
        alertField.setAdapter(
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                optionsHolder.options.values.toTypedArray()
            )
        )

        // restore value or set default
        if (newSubscriptionViewModel.alertInputSelection == "") {
            newSubscriptionViewModel.alertInputSelection = optionsHolder.default
        }
        alertField.setText(newSubscriptionViewModel.alertInputSelection, false)

        // handle new value
        alertField.doAfterTextChanged {
            newSubscriptionViewModel.alertInputSelection = it.toString()
        }
    }

    private fun getInputErrorStringByState(newState: BaseViewModel.InputState): String {
        return when (newState) {
            is BaseViewModel.InputState.Initial -> { "" }
            is BaseViewModel.InputState.Correct -> { "" }
            is BaseViewModel.InputState.Empty -> {
                getString(R.string.subscription_e_f_field_error_empty)
            }
            is BaseViewModel.InputState.Wrong -> {
                getString(R.string.subscription_e_f_field_error_wrong)
            }
        }
    }

    private fun parseSubscription(): Subscription {
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
        val renewalPeriod = Period.parse(
            RenewalPeriodOptions(resources).options
                .getFirstKey(binding.actvNewSubscriptionRenewalPeriod.text.toString())
        )

        // alert period
        val alertEnabled: Boolean
        val alertPeriod: Period
        val alertPeriodOptions = AlertPeriodOptions(resources)
        val alertPeriodValue = binding.actvNewSubscriptionAlert.text.toString()
        if (alertPeriodValue == alertPeriodOptions.default) {
            alertEnabled = false
            alertPeriod = Period.ZERO
        } else {
            alertEnabled = true
            alertPeriod = Period.parse(
                alertPeriodOptions.options.getFirstKey(alertPeriodValue)
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
