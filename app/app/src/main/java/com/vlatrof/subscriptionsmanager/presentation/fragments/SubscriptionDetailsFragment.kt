package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionDetailsBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.utils.*
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Currency

class SubscriptionDetailsFragment : Fragment(R.layout.fragment_subscription_details) {

    companion object {
        const val ARGUMENT_SUBSCRIPTION_ID_TAG = "ARGUMENT_SUBSCRIPTION_ID"
        const val ARGUMENT_SUBSCRIPTION_ID_DEFAULT_VALUE = -1
    }

    private val subscriptionDetailsViewModel by viewModel<SubscriptionDetailsViewModel>()
    private lateinit var binding: FragmentSubscriptionDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubscriptionDetailsBinding.bind(view)

        loadSubscription()
        observeSubscriptionLiveData()
        setupGoBackButton()
        setupNameTitle()
        setupNextRenewalTitle()
        setupNameInput()
        setupCostInput()
        setupStartDateInput()
        setupSaveButton()
        setupDeleteButton()
    }

    override fun onResume() {
        super.onResume()
        setupCurrencyInput()
        setupRenewalPeriodInput()
        setupAlertPeriodInput()
    }

    private fun loadSubscription() {
        try {
            subscriptionDetailsViewModel.loadSubscriptionById(getArgumentSubscriptionId())
        } catch (exception: IllegalArgumentException) {
            showToast(getString(R.string.toast_error_message_something_went_wrong))
            findNavController().popBackStack()
        }
    }

    private fun observeSubscriptionLiveData() {
        subscriptionDetailsViewModel.subscriptionLiveData.observe(viewLifecycleOwner) {
            populateUi(subscription = it)
        }
    }

    private fun setupGoBackButton() {
        binding.btnSubscriptionDetailsGoBack.setOnClickListener {
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun setupNameTitle() {
        subscriptionDetailsViewModel.nameTitleLiveData.observe(viewLifecycleOwner) {
            binding.tvSubscriptionDetailsNameTitle.text = it
        }
    }

    private fun setupNextRenewalTitle() {
        subscriptionDetailsViewModel.nextRenewalTitleLiveData.observe(viewLifecycleOwner) {
            binding.tvSubscriptionDetailsNextRenewalTitle.text = it
        }
    }

    private fun setupNameInput() {

        // handle new value after text changed
        binding.tietSubscriptionDetailsName.doAfterTextChanged {
            val newValue = it.toString()
            subscriptionDetailsViewModel.handleNewNameInputValue(newValue)
            subscriptionDetailsViewModel.handleNewNameTitleValue(newValue)
        }

        // handle new state
        subscriptionDetailsViewModel.nameInputState.observe(viewLifecycleOwner) {
            binding.tilSubscriptionDetailsName.error = getString(it.errorStringResourceId)
        }

    }

    private fun setupCostInput() {

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
            .setSelection(subscriptionDetailsViewModel.startDateInputSelectionLiveData.value)
            .setTitleText(getString(R.string.subscription_e_f_til_start_date_date_picker_title_text))
            .build()
            .apply {
                addOnPositiveButtonClickListener { selection ->
                    subscriptionDetailsViewModel.handleNewStartDateValue(selection)
                }
            }

        // handle click on field
        dateField.setOnClickListener {
            datePicker.show(
                parentFragmentManager,
                getString(R.string.subscription_e_f_til_start_date_date_picker_tag)
            )
        }

        // handle new date selection
        subscriptionDetailsViewModel.startDateInputSelectionLiveData.observe(viewLifecycleOwner) { newSelection ->

            val newSelectedDate = Parser.parseLocalDateFromUTCMilliseconds(newSelection)

            binding.tietSubscriptionDetailsStartDate.setText(
                newSelectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
            )

        }

    }

    private fun setupSaveButton() {

        // handle click
        binding.btnSubscriptionDetailsSave.setOnClickListener{
            subscriptionDetailsViewModel.updateSubscription(parseSubscription())
            hideKeyboard()
            findNavController().popBackStack()
        }

        // handle new state
        subscriptionDetailsViewModel.buttonSaveState.observe(viewLifecycleOwner) { enabled ->
            binding.btnSubscriptionDetailsSave.apply {
                isEnabled = enabled
                setTextColor(
                    if (enabled) ResourcesCompat.getColor(resources, R.color.green, null)
                    else ResourcesCompat.getColor(resources, R.color.gray, null)
                )
            }
        }

    }

    private fun setupDeleteButton() {

        binding.btnSubscriptionDetailsDelete.setOnClickListener {

            MaterialAlertDialogBuilder(requireActivity(), R.style.Alert_dialog_delete_subscription)
                .setTitle(R.string.subscription_details_delete_dialog_title)
                .setMessage(R.string.subscription_details_delete_dialog_message)
                .setPositiveButton(R.string.subscription_details_delete_dialog_btn_positive) { _, _ -> onPositiveActionDialogDelete() }
                .setNegativeButton(R.string.subscription_details_delete_dialog_btn_negative) { _, _ -> }
                .show()

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

        val renewalPeriodField = binding.actvSubscriptionDetailsRenewalPeriod

        // set menu items
        renewalPeriodField.setAdapter(ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            RenewalPeriodOptionsHolder(resources).options.values.toTypedArray()
        ))

        // restore value from viewmodel
        val restoredValue = subscriptionDetailsViewModel.restoreRenewalPeriodValue()
        renewalPeriodField.setText(restoredValue, false)

        // handle new value
        renewalPeriodField.doAfterTextChanged {
            subscriptionDetailsViewModel.handleNewRenewalPeriodValue(it.toString())
        }

    }

    private fun setupAlertPeriodInput() {

        val alertField = binding.actvSubscriptionDetailsAlert

        // set menu items
        alertField.setAdapter(ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            AlertPeriodOptionsHolder(resources).options.values.toTypedArray()
        ))

        // restore value from viewmodel
        alertField.setText(subscriptionDetailsViewModel.alertInputValue, false)

        // handle new value
        alertField.doAfterTextChanged {
            subscriptionDetailsViewModel.handleNewAlertValue(it.toString())
        }

    }

    private fun populateUi(subscription: Subscription) {

        // name title
        binding.tvSubscriptionDetailsNameTitle.text = subscription.name
        subscriptionDetailsViewModel.handleNewNameTitleValue(subscription.name)

        // next renewal title
        subscriptionDetailsViewModel.handleNewNextRenewalDate(subscription.nextRenewalDate)

        // name input
        binding.tietSubscriptionDetailsName.setText(subscription.name)
        subscriptionDetailsViewModel.handleNewNameInputValue(subscription.name)

        // description input
        binding.tietSubscriptionDetailsDescription.setText(subscription.description)

        // cost input
        val costStr = subscription.paymentCost.toString()
        binding.tietSubscriptionDetailsCost.setText(costStr)
        subscriptionDetailsViewModel.handleNewCostInputValue(costStr)

        // currency input
        val currencyStr = subscription.paymentCurrency.currencyCode
        binding.actvSubscriptionDetailsCurrency.setText(currencyStr)
        subscriptionDetailsViewModel.handleNewCurrencyValue(currencyStr)

        // start date input
        subscriptionDetailsViewModel.handleNewStartDateValue(
            subscription.startDate.toUTCMilliseconds()
        )

        // renewal period input
        val renewalPeriodStr = RenewalPeriodOptionsHolder(resources).options[subscription.renewalPeriod.toString()]
        binding.actvSubscriptionDetailsRenewalPeriod.setText(renewalPeriodStr, false)
        subscriptionDetailsViewModel.handleNewRenewalPeriodValue(renewalPeriodStr!!)

        // alert period input
        val alertPeriodOptionsHolder = AlertPeriodOptionsHolder(resources)
        val alertPeriodStr = if (subscription.alertEnabled) {
            alertPeriodOptionsHolder.options[subscription.alertPeriod.toString()]!!
        } else {
            alertPeriodOptionsHolder.defaultValue
        }
        binding.actvSubscriptionDetailsAlert.setText(alertPeriodStr, false)
        subscriptionDetailsViewModel.handleNewAlertValue(alertPeriodStr)

    }

    private fun parseSubscription() : Subscription {

        val id = requireArguments().getInt(
            ARGUMENT_SUBSCRIPTION_ID_TAG, ARGUMENT_SUBSCRIPTION_ID_DEFAULT_VALUE
        )

        val name = binding.tietSubscriptionDetailsName.text.toString().trim()

        val description = binding.tietSubscriptionDetailsDescription.text.toString().trim()

        val paymentCost =
            binding.tietSubscriptionDetailsCost.text.toString().toDouble().round(2)

        val paymentCurrency = Currency.getInstance(
            binding.actvSubscriptionDetailsCurrency.text.toString()
        )

        val startDate = Parser.parseLocalDateFromUTCMilliseconds(
            subscriptionDetailsViewModel.startDateInputSelectionLiveData.value!!
        )

        // renewal period
        val renewalPeriod = Period.parse(RenewalPeriodOptionsHolder(resources).options
            .getFirstKey(binding.actvSubscriptionDetailsRenewalPeriod.text.toString())
        )

        // alert period
        val alertEnabled: Boolean
        val alertPeriod: Period
        val alertPeriodOptionsHolder = AlertPeriodOptionsHolder(resources)
        val alertPeriodValue = binding.actvSubscriptionDetailsAlert.text.toString()
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
            id = id,
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

    private fun onPositiveActionDialogDelete() {
        subscriptionDetailsViewModel.deleteSubscriptionById(getArgumentSubscriptionId())
        hideKeyboard()
        findNavController().popBackStack()
    }

    private fun getArgumentSubscriptionId(): Int {
        return requireArguments().getInt(
            ARGUMENT_SUBSCRIPTION_ID_TAG, ARGUMENT_SUBSCRIPTION_ID_DEFAULT_VALUE
        )
    }

}

