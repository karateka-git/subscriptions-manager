package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptions

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.vlatrof.subscriptionsmanager.R.layout.fragment_subscriptions
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionsBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription

class SubscriptionsFragment : Fragment(fragment_subscriptions) {

    private lateinit var binding: FragmentSubscriptionsBinding
    private lateinit var subscriptionsListAdapter: SubscriptionsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSubscriptionsBinding.bind(view)
        subscriptionsListAdapter = SubscriptionsListAdapter()
        binding.rvSubscriptionsList.adapter = subscriptionsListAdapter

        subscriptionsListAdapter.setData(
            newSubscriptionsList = listOf(
                Subscription("Yandex Plus", 450.0),
                Subscription("Yandex Plus", 450.0),
                Subscription("Yandex Plus", 450.0),
                Subscription("Yandex Plus Plus", 4520.0),
                Subscription("Yandex Plus Plus", 4520.0),
                Subscription("Yandex Plus Plus", 4520.0),
                Subscription("Yandex Plus", 450.0),
                Subscription("Yandex Plus", 450.0),
                Subscription("Yandex Plus", 450.0),
                Subscription("Yandex Plus Plus", 4520.0),
                Subscription("Yandex Plus Plus", 4520.0),
                Subscription("Yandex Plus Plus", 4520.0),
                Subscription("Yandex Plus", 450.0),
                Subscription("Yandex Plus", 450.0),
                Subscription("Yandex Plus", 450.0),
                Subscription("Yandex Plus Plus", 4520.0),
                Subscription("Yandex Plus Plus", 4520.0),
                Subscription("Yandex Plus Plus", 4520.0),
            )
        )

        AppCompatDelegate. setDefaultNightMode(AppCompatDelegate. MODE_NIGHT_YES)

    }


}