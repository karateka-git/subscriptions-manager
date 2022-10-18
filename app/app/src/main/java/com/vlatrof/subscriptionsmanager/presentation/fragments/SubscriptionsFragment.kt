package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionsBinding
import com.vlatrof.subscriptionsmanager.presentation.adapters.SubscriptionsActionListener
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
    }

    private fun setupSubscriptionsRVAdapter() {

        val listener = object: SubscriptionsActionListener {
            override fun onSubscriptionItemClick(subscriptionId: Int) {
                openSubscriptionDetailsScreen(subscriptionId)
            }
        }
        subscriptionsAdapter = SubscriptionsAdapter(requireActivity(), listener)
        binding.rvSubscriptionsList.adapter = subscriptionsAdapter
    }

    private fun startToObserveSubscriptionsLiveData() {

        subscriptionsViewModel.subscriptionsLiveData.observe(viewLifecycleOwner) {
            updatedSubscriptionsList ->
            subscriptionsAdapter.setData(updatedSubscriptionsList)
        }
    }

    private fun setupNewSubscriptionButton() {

        binding.btnNewSubscription.setOnClickListener{
            findNavController().navigate(
                R.id.action_fragment_subscriptions_list_to_fragment_new_subscription
            )
        }
    }

    private fun openSubscriptionDetailsScreen(subscriptionId: Int) {

        findNavController().navigate(
            R.id.action_fragment_subscriptions_list_to_fragment_subscription_details,
            Bundle().apply {
                putInt(SubscriptionDetailsFragment.ARGUMENT_SUBSCRIPTION_ID_TAG,
                    subscriptionId
                )
            }
        )
    }

}