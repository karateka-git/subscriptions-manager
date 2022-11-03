package com.vlatrof.subscriptionsmanager.presentation.screens.base

import androidx.lifecycle.ViewModel
import com.vlatrof.subscriptionsmanager.R

abstract class BaseViewModel : ViewModel() {

    enum class InputState(val errorStringResourceId: Int) {
        INITIAL(errorStringResourceId = R.string.subscription_e_f_field_error_none),
        CORRECT(errorStringResourceId = R.string.subscription_e_f_field_error_none),
        WRONG(errorStringResourceId = R.string.subscription_e_f_field_error_wrong),
        EMPTY(errorStringResourceId = R.string.subscription_e_f_field_error_empty)
    }
}
