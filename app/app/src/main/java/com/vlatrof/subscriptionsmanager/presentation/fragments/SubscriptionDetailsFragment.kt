package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionDetailsBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.utils.hideKeyboard
import com.vlatrof.subscriptionsmanager.presentation.utils.showToast
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.IllegalStateException

class SubscriptionDetailsFragment : Fragment(R.layout.fragment_subscription_details) {

    private lateinit var binding: FragmentSubscriptionDetailsBinding
    private val subscriptionDetailsViewModel by viewModel<SubscriptionDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubscriptionDetailsBinding.bind(view)
        loadSubscription()
        observeSubscriptionLiveData()
        setupGoBackButton()
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
            populateUi(it)
        }
    }

    private fun setupGoBackButton() {
        binding.btnSubscriptionDetailsGoBack.setOnClickListener {
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun populateUi(subscription: Subscription) {
        // todo: not implemented yet
    }

    companion object {
        const val ARGUMENT_SUBSCRIPTION_ID_TAG = "ARGUMENT_SUBSCRIPTION_ID_TAG"
        const val ARGUMENT_SUBSCRIPTION_ID_DEFAULT_VALUE = -1
    }

}