package com.vlatrof.subscriptionsmanager.presentation.screens.subscriptions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlatrof.subscriptionsmanager.databinding.RvItemSubscriptionBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription

class SubscriptionsListAdapter : RecyclerView.Adapter<SubscriptionsListAdapter.SubscriptionViewHolder>() {

    var subscriptions: List<Subscription> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newSubscriptionsList: List<Subscription>) {
        subscriptions = newSubscriptionsList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = subscriptions.size

    class SubscriptionViewHolder(val binding: RvItemSubscriptionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {

        val binding = RvItemSubscriptionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return SubscriptionViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {

        val s = subscriptions[position]
        val b = holder.binding

        val costStr = "${s.paymentCost} ${s.paymentCurrency.currencyCode} / ${s.renewalPeriod.toString().drop(1)}"

        b.tvSubscriptionTitle.text = s.title
        b.tvSubscriptionCost.text = costStr

    }

}