package com.vlatrof.subscriptionsmanager.presentation.screens.options

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vlatrof.subscriptionsmanager.R
import com.vlatrof.subscriptionsmanager.databinding.FragmentOptionsBinding
import com.vlatrof.subscriptionsmanager.utils.getFirstKey
import org.koin.androidx.viewmodel.ext.android.viewModel

class OptionsFragment : Fragment(R.layout.fragment_options) {

    private val optionsViewModel by viewModel<OptionsViewModel>()
    private lateinit var binding: FragmentOptionsBinding
    private lateinit var filePickerLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var directoryPickerLauncher: ActivityResultLauncher<Uri?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerFilePickerLauncher()
        registerDirectoryPickerLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOptionsBinding.bind(view)
        setupCloseOptionsButton()
        setupNightModeRadioGroup()
        setupImportSubscriptionsButton()
        setupExportSubscriptionsButton()
    }

    private fun registerFilePickerLauncher() {
        filePickerLauncher =
            registerForActivityResult(ActivityResultContracts.OpenDocument()) { contentUri ->
                if (contentUri != null) {
                    optionsViewModel.importSubscriptions(contentUri)
                }
            }
    }

    private fun registerDirectoryPickerLauncher() {
        directoryPickerLauncher =
            registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { dirUri ->
                if (dirUri != null) {
                    optionsViewModel.exportSubscriptions(dirUri)
                }
            }
    }

    private fun setupImportSubscriptionsButton() {
        binding.btnOptionsImportSubscriptions.setOnClickListener() {
            filePickerLauncher.launch(arrayOf("*/*"))
        }
    }

    private fun setupExportSubscriptionsButton() {
        binding.btnOptionsExportSubscriptions.setOnClickListener() {
            directoryPickerLauncher.launch(null)
        }
    }

    private fun setupCloseOptionsButton() {
        binding.btnOptionsClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupNightModeRadioGroup() {
        val radioGroupNightModeMap = mapOf(
            MODE_NIGHT_NO to binding.rbDay.id,
            MODE_NIGHT_YES to binding.rbNight.id,
            MODE_NIGHT_FOLLOW_SYSTEM to binding.rbSystem.id
        )

        binding.rgOptionsNightMode.check(
            radioGroupNightModeMap[optionsViewModel.getCurrentNightMode()]!!
        )

        binding.rgOptionsNightMode.setOnCheckedChangeListener { _, checkedId ->
            optionsViewModel.applyNightMode(radioGroupNightModeMap.getFirstKey(checkedId)!!)
        }
    }
}
