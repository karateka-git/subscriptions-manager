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

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Double.round(places: Int): Double {

    if (places < 0) {
        throw IllegalArgumentException()
    }

    return BigDecimal
        .valueOf(this)
        .setScale(places, RoundingMode.DOWN)
        .toDouble()

}