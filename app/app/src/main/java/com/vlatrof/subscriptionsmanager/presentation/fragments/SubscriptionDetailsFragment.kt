package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentSubscriptionDetailsBinding

class SubscriptionDetailsFragment : Fragment(R.layout.fragment_subscription_details) {

    private lateinit var binding: FragmentSubscriptionDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubscriptionDetailsBinding.bind(view)
        binding.tvTest.text = arguments?.getInt("id").toString()
    }

}