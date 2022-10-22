package com.vlatrof.subscriptionsmanager.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentOptionsBinding
import com.vlatrof.subscriptionsmanager.presentation.viewmodels.OptionsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OptionsFragment : Fragment(R.layout.fragment_options) {

    private val optionsViewModel by viewModel<OptionsViewModel>()
    private lateinit var binding: FragmentOptionsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOptionsBinding.bind(view)
        setupCloseOptionsButton()
        setupNightModeSwitcher()
    }

    private fun setupCloseOptionsButton() {
        binding.btnOptionsClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupNightModeSwitcher() {

        binding.swOptionsNightMode.isChecked =
            (optionsViewModel.getCurrentNightMode() == MODE_NIGHT_YES)

        binding.swOptionsNightMode.setOnCheckedChangeListener { _, isChecked ->

            val newNightMode = if (isChecked) MODE_NIGHT_YES else MODE_NIGHT_NO

            optionsViewModel.saveNewNightMode(newNightMode)

            if (!optionsViewModel.alertNightModeWasShown) {
                showAlertDialogNightModeChanged()
                optionsViewModel.alertNightModeWasShown = true
            }

        }

    }

    private fun showAlertDialogNightModeChanged() {

        MaterialAlertDialogBuilder(requireActivity(), R.style.Alert_dialog_info)
            .setTitle(getString(R.string.options_alert_dialog_night_mode_title))
            .setMessage(getString(R.string.options_alert_dialog_night_mode_message))
            .setPositiveButton("Ok" ) {_,_ ->}
            .show()

    }

}
