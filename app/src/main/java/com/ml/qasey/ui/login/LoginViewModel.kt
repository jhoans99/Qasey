package com.ml.qasey.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.ml.qasey.data.LoginRepository
import com.ml.qasey.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState


    fun updateUserNameValue(value: String) {
        _uiState.value = _uiState.value.copy(userName = value)
    }

    fun updatePasswordValue(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun login(
        onSuccessLogin:() -> Unit
    ) {
        viewModelScope.launch {
            loginRepository.login(_uiState.value.userName,_uiState.value.password).collect {
                when(it) {
                    is Result.Error -> {
                       _uiState.value = _uiState.value.copy(isLoading = false)
                        updateModalError(true)
                    }
                    Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onSuccessLogin()
                    }
                }
            }
        }
    }

    fun updateModalError(value: Boolean) {
        _uiState.value = _uiState.value.copy(isModalError = value)
    }

}