package com.ml.qasey.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class LoginViewModel: ViewModel() {

    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun updateUserNameValue(value: String) {
        _uiState.value = _uiState.value.copy(userName = value)
    }

    fun updatePasswordValue(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }



}