package com.vlatrof.subscriptionsmanager.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "subscriptions")
data class SubscriptionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "name", defaultValue = "")
    val name: String = "",

    @ColumnInfo(name = "description", defaultValue = "")
    val description: String = "",

    @ColumnInfo(name = "payment_cost")
    val paymentCost: Double = 0.0,

    // Payment currency as a currency code (example: USD)
    @ColumnInfo(name = "payment_currency_code")
    val paymentCurrencyCode: String = "",

    // Date in ISO Date format (example: 2020-07-25)
    @ColumnInfo(name = "start_date")
    val startDate: String = "",

    // Renewal period in java.time.Period pattern (example: P1M - 1 month)
    @ColumnInfo(name = "renewal_period")
    val renewalPeriod: String = "",

    @ColumnInfo(name = "alert_enabled")
    val alertEnabled: Boolean = false,

    // Alert period in java.time.Period pattern (example: P-1D - minus 1 day)
    @ColumnInfo(name = "alert_period")
    val alertPeriod: String = ""

)
