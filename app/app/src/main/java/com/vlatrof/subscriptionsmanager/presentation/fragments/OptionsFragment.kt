package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.app.App
import com.vlatrof.subscriptionsmanager.databinding.FragmentOptionsBinding

class OptionsFragment : Fragment(R.layout.fragment_options) {

    private lateinit var binding: FragmentOptionsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var currentNightMode: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOptionsBinding.bind(view)
        sharedPreferences = getSharedPrefs()
        currentNightMode = sharedPreferences.getInt(App.NIGHT_MODE, -1)
        setupCloseOptionsButton()
        setupNightModeSwitcher()
    }

    private fun getSharedPrefs(): SharedPreferences {
        return requireActivity().getSharedPreferences(App.APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    private fun setupCloseOptionsButton() {
        binding.btnOptionsClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupNightModeSwitcher() {

        binding.swOptionsNightMode.isChecked = (currentNightMode == MODE_NIGHT_YES)

        binding.swOptionsNightMode.setOnCheckedChangeListener { _, isChecked ->

            val newNightMode = if (isChecked) MODE_NIGHT_YES else MODE_NIGHT_NO

            sharedPreferences
                .edit()
                .putInt(App.NIGHT_MODE, newNightMode)
                .apply()

            if (newNightMode != currentNightMode) {

                MaterialAlertDialogBuilder(requireActivity(), R.style.AlertDialogDeleteSubscriptionTheme)
                    .setTitle(getString(R.string.options_alert_dialog_night_mode_title))
                    .setMessage(getString(R.string.options_alert_dialog_night_mode_message))
                    .setPositiveButton("Ok" ) {_,_ ->}
                    .show()

            }

        }

    }

}
