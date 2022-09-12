package com.vlatrof.subscriptionsmanager.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class SubscriptionEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "name", defaultValue = "")
    val name: String = "",

    @ColumnInfo(name = "description", defaultValue = "")
    val description: String = "",

    // Date in ISO Date format (example: 2020-07-25)
    @ColumnInfo(name = "start_date")
    val startDate: String = "",

    // Renewal period as LocalDate.toString() form (example: P1M - 1 month period)
    @ColumnInfo(name = "renewal_period")
    val renewalPeriod: String = "",

    // Payment cost in smallest units in corresponding currency
    @ColumnInfo(name = "payment_cost")
    val paymentCost: Long = 0,

    // Payment currency as a currency code (example: USD)
    @ColumnInfo(name = "payment_currency_code")
    val paymentCurrencyCode: String = ""

)