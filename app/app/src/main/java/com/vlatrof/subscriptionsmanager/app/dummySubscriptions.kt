package com.vlatrof.subscriptionsmanager.app

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import java.time.LocalDate
import java.time.Period
import java.util.*

val dummySubscriptionsList = listOf(

    Subscription(
        name = "Yandex Plus",
        description = "Some useful subscription",
        startDate = LocalDate.of(2022, 8, 15),
        renewalPeriod = Period.ofMonths(1),
        paymentCost = 249,
        paymentCurrency = Currency.getInstance("RUB")
    ),

    Subscription(
        name = "Figma",
        description = "Some useful subscription",
        startDate = LocalDate.of(2012, 9, 25),
        renewalPeriod = Period.ofYears(1),
        paymentCost = 5,
        paymentCurrency = Currency.getInstance("USD")
    ),

    Subscription(
        name = "Spotify Premium",
        description = "Some useful subscription",
        startDate = LocalDate.of(2022, 8, 15),
        renewalPeriod = Period.ofMonths(1),
        paymentCost = 149,
        paymentCurrency = Currency.getInstance("RUB")
    ),

    Subscription(
        name = "Youtube Premium",
        description = "Some useful subscription",
        startDate = LocalDate.of(2022, 8, 15),
        renewalPeriod = Period.ofWeeks(2),
        paymentCost = 199,
        paymentCurrency = Currency.getInstance("AUD")
    ),

    Subscription(
        name = "Tinkoff Pro",
        description = "Some useful subscription",
        startDate = LocalDate.of(2022, 8, 15),
        renewalPeriod = Period.ofMonths(1),
        paymentCost = 249,
        paymentCurrency = Currency.getInstance("RUB")
    ),

    )


//class DatePickerFragment(
//
//    private val dateView: TextInputEditText
//
//) : DialogFragment(), DatePickerDialog.OnDateSetListener {
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//        // Use the current date as the default date in the picker
//        val c = Calendar.getInstance()
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)
//
//        // Create a new instance of DatePickerDialog and return it
//        return DatePickerDialog(requireContext(), this, year, month, day)
//
//    }
//
//    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
//        val newDateStr = "${day}/${month}/${year}"
//        dateView.setText(newDateStr)
//    }
//
//}

//val datePicker = DatePickerFragment(binding.startDatePicker)
//datePicker.show(parentFragmentManager, "datePicker")