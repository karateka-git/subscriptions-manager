package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vlatrof.subscriptionsmanager.R.layout.fragment_subscriptions
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionsBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import com.vlatrof.subscriptionsmanager.presentation.adapters.SubscriptionsAdapter
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.SubscriptionsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.Period
import java.util.*

class SubscriptionsFragment : Fragment(fragment_subscriptions) {

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
            val dummySubscriptionsList = listOf(

                Subscription(
                    title = "Yandex Plus",
                    startDate = LocalDate.of(2022, 8, 15),
                    renewalPeriod = Period.ofMonths(1),
                    paymentCost = 249,
                    paymentCurrency = Currency.getInstance("RUB")
                ),

                Subscription(
                    title = "Figma",
                    startDate = LocalDate.of(2012, 9, 25),
                    renewalPeriod = Period.ofYears(1),
                    paymentCost = 5,
                    paymentCurrency = Currency.getInstance("USD")
                ),

                Subscription(
                    title = "Spotify Premium",
                    startDate = LocalDate.of(2022, 8, 15),
                    renewalPeriod = Period.ofMonths(1),
                    paymentCost = 149,
                    paymentCurrency = Currency.getInstance("RUB")
                ),

                Subscription(
                    title = "Youtube Premium",
                    startDate = LocalDate.of(2022, 8, 15),
                    renewalPeriod = Period.ofWeeks(2),
                    paymentCost = 199,
                    paymentCurrency = Currency.getInstance("AUD")
                ),

                Subscription(
                    title = "Tinkoff Pro",
                    startDate = LocalDate.of(2022, 8, 15),
                    renewalPeriod = Period.ofMonths(1),
                    paymentCost = 249,
                    paymentCurrency = Currency.getInstance("RUB")
                ),

                )
            subscriptionsViewModel.insertNewSubscription(
                dummySubscriptionsList[0]
            )
        }

    }
    
}