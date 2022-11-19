package com.vlatrof.subscriptionsmanager.presentation.screens.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    sealed class InputState {
        object Initial : InputState()
        object Correct : InputState()
        object Wrong : InputState()
        object Empty : InputState()
    }
}
