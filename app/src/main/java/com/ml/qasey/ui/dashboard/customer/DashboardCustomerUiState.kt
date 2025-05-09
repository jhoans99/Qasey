package com.ml.qasey.ui.dashboard.customer

data class DashboardCustomerUiState(
    val isLoading: Boolean = false,
    val timer: Int = 0,
    val numberCase: String = "",
    val isShowModalTypeCase: Boolean = false,
    val isSuccessCreateCase: Boolean = false,
    val isFailureCreateCase: Boolean = false
)
