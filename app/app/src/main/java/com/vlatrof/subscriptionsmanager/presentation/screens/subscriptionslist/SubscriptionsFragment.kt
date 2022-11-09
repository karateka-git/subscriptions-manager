package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptionslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionsBinding
import com.vlatrof.subscriptionsmanager.presentation.screens.subscriptiondetails.SubscriptionDetailsFragment
import javax.inject.Inject

class SubscriptionsFragment : Fragment(R.layout.fragment_subscriptions) {

    @Inject lateinit var subscriptionsViewModelFactory: SubscriptionsViewModelFactory
    private lateinit var subscriptionsViewModel: SubscriptionsViewModel
    private lateinit var binding: FragmentSubscriptionsBinding
    private lateinit var subscriptionsAdapter: SubscriptionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        createSubscriptionsViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubscriptionsBinding.bind(view)
        setupSubscriptionsRVAdapter()
        observeSubscriptionsLiveData()
        setupOpenOptionsButton()
        setupNewSubscriptionButton()
        setupNotFoundLayoutNewSubscriptionButton()
    }

    private fun injectDependencies() {
        (requireActivity().applicationContext as App).appComponent.inject(this)
    }

    private fun createSubscriptionsViewModel() {
        subscriptionsViewModel = ViewModelProvider(
            this,
            subscriptionsViewModelFactory
        )[SubscriptionsViewModel::class.java]
    }

    private fun setupNotFoundLayoutNewSubscriptionButton() {
        binding.btnLlSubscriptionsNotFoundAddNew.setOnClickListener {
            findNavController().navigate(
                R.id.action_fragment_subscriptions_list_to_fragment_new_subscription
            )
        }
    }

    private fun setupSubscriptionsRVAdapter() {
        val listener = object : SubscriptionsActionListener {
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
        binding.btnSubscriptionsAddNew.setOnClickListener {
            findNavController().navigate(
                R.id.action_fragment_subscriptions_list_to_fragment_new_subscription
            )
        }
    }

    private fun openSubscriptionDetailsScreen(subscriptionId: Int) {
        findNavController().navigate(
            R.id.action_fragment_subscriptions_list_to_fragment_subscription_details,
            Bundle().apply {
                putInt(
                    SubscriptionDetailsFragment.ARGUMENT_SUBSCRIPTION_ID_TAG,
                    subscriptionId
                )
            }
        )
    }

    private fun setupOpenOptionsButton() {
        binding.btnOpenOptions.setOnClickListener {
            findNavController().navigate(
                R.id.action_fragment_subscriptions_list_to_fragment_options
            )
        }
    }
}
