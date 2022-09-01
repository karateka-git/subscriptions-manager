package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vlatrof.subscriptionsmanager.R.layout.fragment_subscriptions
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriptionsFragment : Fragment(fragment_subscriptions) {

    private lateinit var binding: FragmentSubscriptionsBinding
    private lateinit var subscriptionsAdapter: SubscriptionsAdapter
    private val subscriptionsViewModel by viewModel<SubscriptionsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSubscriptionsBinding.bind(view)
        subscriptionsAdapter = SubscriptionsAdapter()
        binding.rvSubscriptionsList.adapter = subscriptionsAdapter
        subscriptionsViewModel.getAllSubscriptions()
        subscriptionsViewModel.subscriptionsLiveData.observe(viewLifecycleOwner) {
            subscriptionsAdapter.setData(newSubscriptionsList = it)
        }

    }

}