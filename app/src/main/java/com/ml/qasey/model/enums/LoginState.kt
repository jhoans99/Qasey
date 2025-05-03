package com.ml.qasey.model.enums

sealed class LoginState {
    data class SUCCESS_LOGIN(val uid: String): LoginState()
    data object ERROR: LoginState()
}