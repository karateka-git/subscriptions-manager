package com.vlatrof.subscriptionsmanager.presentation.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

// Show Toast

fun Fragment.showToast(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}

// Hide Keyboard

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

// Round double value

fun Double.round(places: Int, mode: RoundingMode = RoundingMode.DOWN): Double {

    if (places < 0) {
        throw IllegalArgumentException()
    }

    return BigDecimal
        .valueOf(this)
        .setScale(places, mode)
        .toDouble()

}

// LocalDate to UTC milliseconds

fun LocalDate.toUTCMilliseconds() : Long {
    return this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}

// get key of first entry of Map with given value

fun <K, V> Map<K, V>.getFirstKey(value: V): K? {
    for (key in this.keys)
        if (this[key] == value)
            return key
    return null
}
