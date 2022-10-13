package com.vlatrof.subscriptionsmanager.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.vlatrof.subscriptionsmanager.databinding.RvItemSubscriptionBinding
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import java.time.LocalDate
import java.time.Period

class SubscriptionsAdapter : RecyclerView.Adapter<SubscriptionsAdapter.SubscriptionViewHolder>() {

    var subscriptions: List<Subscription> = emptyList()

    fun setData(newSubscriptionsList: List<Subscription>) {

        val subscriptionsDiffUtilCallback =
            SubscriptionsDiffUtilCallback(subscriptions, newSubscriptionsList)

        val diffResult = DiffUtil.calculateDiff(subscriptionsDiffUtilCallback)

        subscriptions = newSubscriptionsList

        diffResult.dispatchUpdatesTo(this@SubscriptionsAdapter)

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

        val subscription = subscriptions[position]

        holder.binding.tvSubscriptionName.text = subscription.name

        val costStr = "${subscription.paymentCost} ${subscription.paymentCurrency.currencyCode}"
        holder.binding.tvSubscriptionCost.text = costStr

        val currentDate = LocalDate.now()
        val startDate = subscription.startDate
        val renewalPeriod = subscription.renewalPeriod
        var nextRenewalDate: LocalDate = LocalDate.from(startDate)

        while (nextRenewalDate < currentDate) {
            nextRenewalDate = renewalPeriod.addTo(startDate) as LocalDate
        }

        val leftToRenewal = Period.between(currentDate, nextRenewalDate).toString()

        holder.binding.tvSubscriptionRenewalPeriodLeft.text = leftToRenewal

    }

}

class SubscriptionsDiffUtilCallback(

    private val oldList: List<Subscription>,
    private val newList: List<Subscription>

    ) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}