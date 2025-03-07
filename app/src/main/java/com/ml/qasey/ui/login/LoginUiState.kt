package com.ml.qasey.ui.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val password: String = ""
)
