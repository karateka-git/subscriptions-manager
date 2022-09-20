package com.vlatrof.subscriptionsmanager.presentation.fragments

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

    private lateinit var binding: FragmentSubscriptionsBinding
    private lateinit var subscriptionsAdapter: SubscriptionsAdapter
    private val subscriptionsViewModel by viewModel<SubscriptionsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSubscriptionsBinding.bind(view)
        subscriptionsAdapter = SubscriptionsAdapter()
        binding.rvSubscriptionsList.adapter = subscriptionsAdapter

        subscriptionsViewModel.subscriptionsLiveData.observe(viewLifecycleOwner) {
            subscriptionsAdapter.setData(newSubscriptionsList = it)
        }

        binding.btnNewSubscription.setOnClickListener{
            findNavController().navigate(
                R.id.action_subscriptions_fragment_to_new_subscription_fragment
            )
        }

        binding.tvSubscriptionsTitle.setOnClickListener{
            subscriptionsViewModel.deleteAllSubscriptions()
        }

    }
    
}