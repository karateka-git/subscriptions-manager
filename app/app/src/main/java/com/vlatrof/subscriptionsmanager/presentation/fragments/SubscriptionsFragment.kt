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
        observeSubscriptionsLiveData()
        setupNewSubscriptionButton()
        setupNotFoundLayoutNewSubscriptionButton()
    }

    private fun setupNotFoundLayoutNewSubscriptionButton() {

        binding.btnLlSubscriptionsNotFoundAddNew.setOnClickListener{
            findNavController().navigate(
                R.id.action_fragment_subscriptions_list_to_fragment_new_subscription
            )
        }

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

    private fun observeSubscriptionsLiveData() {

        subscriptionsViewModel.subscriptionsLiveData.observe(viewLifecycleOwner) {
            updatedSubscriptionsList ->

            if (updatedSubscriptionsList.isEmpty()) {
                binding.rvSubscriptionsList.visibility = View.GONE
                binding.llSubscriptionsNotFound.visibility = View.VISIBLE
            } else {
                binding.llSubscriptionsNotFound.visibility = View.GONE
                binding.rvSubscriptionsList.visibility = View.VISIBLE
            }

            subscriptionsAdapter.setData(updatedSubscriptionsList)
        }
    }

    private fun setupNewSubscriptionButton() {

        binding.btnSubscriptionsAddNew.setOnClickListener{
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