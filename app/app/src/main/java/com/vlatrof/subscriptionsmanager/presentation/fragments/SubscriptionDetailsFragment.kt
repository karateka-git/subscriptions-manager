package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionDetailsBinding
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriptionDetailsFragment : Fragment(R.layout.fragment_subscription_details) {

    private lateinit var binding: FragmentSubscriptionDetailsBinding
    private val subscriptionDetailsViewModel by viewModel<SubscriptionDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubscriptionDetailsBinding.bind(view)

        val subscriptionId = arguments?.getInt("id") ?: return
        subscriptionDetailsViewModel.loadSubscriptionById(subscriptionId)
        subscriptionDetailsViewModel.subscriptionLiveData.observe(viewLifecycleOwner) {
            binding.tvTest.text = it.toString()
        }
    }

}