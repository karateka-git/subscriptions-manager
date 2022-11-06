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
    private lateinit var exportLauncher: ActivityResultLauncher<Uri?>
    private lateinit var importLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerExportLauncher()
        registerImportLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOptionsBinding.bind(view)
        setupCloseOptionsButton()
        setupNightModeRadioGroup()
        setupExportSubscriptionsButton()
        setupImportSubscriptionsButton()
    }

    private fun setupExportSubscriptionsButton() {
        binding.btnOptionsExportSubscriptions.setOnClickListener {
            exportLauncher.launch(null)
        }
    }

    private fun setupImportSubscriptionsButton() {
        binding.btnOptionsImportSubscriptions.setOnClickListener {
            importLauncher.launch(arrayOf("*/*"))
        }
    }

    private fun registerExportLauncher() {
        exportLauncher =
            registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { directoryUri ->
                onExportSubscriptions(directoryUri)
            }
    }

    private fun registerImportLauncher() {
        importLauncher =
            registerForActivityResult(ActivityResultContracts.OpenDocument()) { contentFileUri ->
                onImportSubscriptions(contentFileUri)
            }
    }

    private fun onExportSubscriptions(directoryUri: Uri?) {
        optionsViewModel.exportSubscriptions(directoryUri)
    }

    private fun onImportSubscriptions(contentFileUri: Uri?) {
        optionsViewModel.importSubscriptions(contentFileUri)
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
