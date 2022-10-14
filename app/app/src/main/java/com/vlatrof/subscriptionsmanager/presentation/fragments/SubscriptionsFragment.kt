package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionsBinding
import com.vlatrof.subscriptionsmanager.presentation.adapters.SubscriptionsAdapter
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriptionsFragment : Fragment(R.layout.fragment_subscriptions) {

    private val subscriptionsViewModel by viewModel<SubscriptionsViewModel>()
    private lateinit var binding: FragmentSubscriptionsBinding
    private lateinit var subscriptionsAdapter: SubscriptionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubscriptionsBinding.bind(view)
        setupSubscriptionsRVAdapter()
        startToObserveSubscriptionsLiveData()
        setupNewSubscriptionButton()

        // todo: for testing ***********************************************************
        binding.btnMenu.setOnClickListener{
            subscriptionsViewModel.deleteAllSubscriptions()
        }
        // todo: for testing ***********************************************************

    }

    private fun setupSubscriptionsRVAdapter() {
        subscriptionsAdapter = SubscriptionsAdapter(requireActivity())
        binding.rvSubscriptionsList.adapter = subscriptionsAdapter
    }

    private fun startToObserveSubscriptionsLiveData() {
        subscriptionsViewModel.subscriptionsLiveData.observe(viewLifecycleOwner) {
            newSubscriptionsList ->
            subscriptionsAdapter.setData(
                newSubscriptionsList.sortedBy { it.nextRenewalDate }
            )
        }
    }

    private fun setupNewSubscriptionButton() {
        binding.btnNewSubscription.setOnClickListener{
            findNavController().navigate(
                R.id.action_fragment_subscriptions_list_to_fragment_new_subscription
            )
        }
    }

}