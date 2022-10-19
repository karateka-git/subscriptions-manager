package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionDetailsBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.utils.*
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class SubscriptionDetailsFragment : Fragment(R.layout.fragment_subscription_details) {

    companion object {
        const val ARGUMENT_SUBSCRIPTION_ID_TAG = "ARGUMENT_SUBSCRIPTION_ID_TAG"
        const val ARGUMENT_SUBSCRIPTION_ID_DEFAULT_VALUE = -1
    }
    
    private lateinit var binding: FragmentSubscriptionDetailsBinding
    private val subscriptionDetailsViewModel by viewModel<SubscriptionDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubscriptionDetailsBinding.bind(view)

        loadSubscription()
        observeSubscriptionLiveData()

        setupGoBackButton()
        setupTitleUpdating()
        setupNameInput()
        setupDescriptionInput()
        setupCostInput()
        setupStartDateInput()
        setupSaveButton()
    }

    override fun onResume() {
        super.onResume()
        setupCurrencyInput()
        setupRenewalPeriodInput()
        setupAlertInput()
    }

    private fun loadSubscription() {
        try {
            val subscriptionId = requireArguments().getInt(
                ARGUMENT_SUBSCRIPTION_ID_TAG, ARGUMENT_SUBSCRIPTION_ID_DEFAULT_VALUE
            )
            if (subscriptionId == ARGUMENT_SUBSCRIPTION_ID_DEFAULT_VALUE) {
                throw IllegalStateException("Empty fragment argument: subscription id")
            }
            subscriptionDetailsViewModel.loadSubscriptionById(subscriptionId)
        } catch (exception: IllegalStateException) {
            showToast(getString(R.string.subscription_details_on_open_error_message))
            findNavController().popBackStack()
        }
    }

    private fun observeSubscriptionLiveData() {
        subscriptionDetailsViewModel.subscriptionLiveData.observe(viewLifecycleOwner) {

            showToast(it.toString())

        }
    }

    private fun setupGoBackButton() {
        binding.btnSubscriptionDetailsGoBack.setOnClickListener {
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun setupTitleUpdating() {

        // handle new name input state
        subscriptionDetailsViewModel.nameInputValueLiveData.observe(viewLifecycleOwner) {
            binding.tvSubscriptionDetailsScreenTitle.text = it
        }

    }

    private fun setupNameInput() {

        // restore value from viewmodel
        binding.tietSubscriptionDetailsName.setText(
            subscriptionDetailsViewModel.nameInputValueLiveData.value
        )

        // handle new value after text changed
        binding.tietSubscriptionDetailsName.doAfterTextChanged {
            subscriptionDetailsViewModel.handleNewNameInputValue(it.toString())
        }

        // handle new state
        subscriptionDetailsViewModel.nameInputState.observe(viewLifecycleOwner) {
            binding.tilSubscriptionDetailsName.error = getString(it.errorStringResourceId)
        }

    }

    private fun setupDescriptionInput() {

        // restore value from viewmodel
        binding.tietSubscriptionDetailsDescription.setText(
            subscriptionDetailsViewModel.descriptionInputValue
        )

        // handle new value after text changed
        binding.tietSubscriptionDetailsName.doAfterTextChanged {
            subscriptionDetailsViewModel.handleNewDescriptionInputValue(it.toString())
        }

    }

    private fun setupCostInput() {

        // restore value from viewmodel
        binding.tietSubscriptionDetailsName.setText(subscriptionDetailsViewModel.costInputValue)

        // handle new value after text changed
        binding.tietSubscriptionDetailsCost.doAfterTextChanged {
            subscriptionDetailsViewModel.handleNewCostInputValue(it.toString())
        }

        // handle new state
        subscriptionDetailsViewModel.costInputState.observe(viewLifecycleOwner) {
            binding.tilSubscriptionDetailsCost.error = getString(it.errorStringResourceId)
        }

    }

    private fun setupStartDateInput() {

        val dateField = binding.tietSubscriptionDetailsStartDate

        // init DatePicker and restore selection from viewmodel
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(subscriptionDetailsViewModel.startDateInputSelection.value)
            .setTitleText(getString(R.string.subscription_e_f_til_start_date_date_picker_title_text))
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    subscriptionDetailsViewModel.handleNewStartDateValue(selection!!)
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
        subscriptionDetailsViewModel.startDateInputSelection.observe(viewLifecycleOwner) { newSelection ->
            binding.tietSubscriptionDetailsStartDate.setText(
                parseLocalDateFromUTCMilliseconds(newSelection)
                    .format(
                        DateTimeFormatter.ofPattern(
                        getString(R.string.subscription_e_f_tiet_start_date_pattern)
                    ))
            )
        }

    }

    private fun setupSaveButton() {

        // handle click
        binding.btnSubscriptionDetailsSave.setOnClickListener{
            // todo: not implemented yet
            // hideKeyboard()
            // findNavController().popBackStack()
        }

        // handle new state
        subscriptionDetailsViewModel.buttonSaveState.observe(viewLifecycleOwner) { enabled ->
            binding.btnSubscriptionDetailsSave.apply {
                isEnabled = enabled
                setTextColor(
                    if (enabled) ResourcesCompat.getColor(resources, R.color.green, null)
                    else ResourcesCompat.getColor(resources, R.color.white_gray, null)
                )
            }
        }

    }

    private fun setupCurrencyInput() {

        val currencyField = binding.actvSubscriptionDetailsCurrency

        // set menu items
        currencyField.setAdapter(ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            Currency.getAvailableCurrencies().toTypedArray()
        ))

        // restore value from viewmodel
        currencyField.setText(subscriptionDetailsViewModel.currencyInputValue, false)

        // handle new value
        currencyField.doAfterTextChanged {

            var newValue = it.toString()

            // force only capital characters
            if (newValue.isNotEmpty() && newValue != newValue.uppercase()) {
                newValue = newValue.uppercase()
                currencyField.setText(newValue)
                currencyField.setSelection(newValue.length)
            }

            subscriptionDetailsViewModel.handleNewCurrencyValue(newValue)

        }

        // handle new input state
        subscriptionDetailsViewModel.currencyInputState.observe(viewLifecycleOwner) { newState ->
            binding.tilSubscriptionDetailsCurrency.error = getString(newState.errorStringResourceId)
        }

    }

    private fun setupRenewalPeriodInput() {

        // set menu items
        binding.actvSubscriptionDetailsRenewalPeriod.setAdapter(ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_renewal_period_options)
                .values.toTypedArray()
        ))

        // restore value from viewmodel
        binding.actvSubscriptionDetailsRenewalPeriod.setText(
            subscriptionDetailsViewModel.renewalPeriodInputValue,
            false
        )

        // handle new value
        binding.actvSubscriptionDetailsRenewalPeriod.doAfterTextChanged { newValue ->
            subscriptionDetailsViewModel.handleNewRenewalPeriodValue(newValue.toString())
        }

    }

    private fun setupAlertInput() {

        // set menu items
        binding.actvSubscriptionDetailsAlert.setAdapter(
            ArrayAdapter(
            activity as Context,
            android.R.layout.simple_spinner_dropdown_item,
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_alert_period_options)
                .values
                .toTypedArray()
        )
        )

        // restore value from viewmodel
        binding.actvSubscriptionDetailsAlert.setText(
            subscriptionDetailsViewModel.alertInputValue,
            false
        )

        // handle new value
        binding.actvSubscriptionDetailsAlert.doAfterTextChanged { newValue ->
            subscriptionDetailsViewModel.handleNewAlertValue(newValue.toString())
        }

    }

    private fun parseSubscription() : Subscription {

        val name = binding.tietSubscriptionDetailsName.text.toString()

        val description = binding.tietSubscriptionDetailsDescription.text.toString()

        val paymentCost =
            binding.tietSubscriptionDetailsCost.text.toString().toDouble().round(2)

        val paymentCurrency = Currency.getInstance(
            binding.actvSubscriptionDetailsCurrency.text.toString()
        )

        // start date
        val startDate = parseLocalDateFromUTCMilliseconds(
            subscriptionDetailsViewModel.startDateInputSelection.value!!
        )

        // renewal period
        val renewalPeriodStr = binding.actvSubscriptionDetailsRenewalPeriod.text.toString()
        val renewalPeriodKey =
            parseXmlResourceMap(requireActivity(), R.xml.map_subscription_renewal_period_options)
                .filterValues{ it == renewalPeriodStr }
                .keys
                .toTypedArray()[0]
        val renewalPeriod = Period.parse(renewalPeriodKey)

        // alert flag and period
        val alertEnabled: Boolean
        val alertPeriod: Period
        val alertPeriodStr = binding.actvSubscriptionDetailsAlert.text.toString()
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

