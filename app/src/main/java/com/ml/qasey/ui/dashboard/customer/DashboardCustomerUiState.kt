package com.ml.qasey.ui.dashboard.customer

import com.ml.qasey.model.cases.CreateCase

data class DashboardCustomerUiState(
    val isLoading: Boolean = false,
    val timer: Int = 0,
    val numberCase: String = "",
    val isShowModalTypeCase: Boolean = false,
    val isSuccessCreateCase: Boolean = false,
    val isFailureCreateCase: Boolean = false,
    val historyCaseList: List<CreateCase> = emptyList(),
    val isShowModalEditCase: Boolean = false,
    val caseEdit: CreateCase = CreateCase(),
    val activeTimers: Map<String, Int> = emptyMap()
)
