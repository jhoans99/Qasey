package com.ml.qasey.ui.dashboard.teamlead

import com.ml.qasey.model.response.User

data class DashboardTeamLeadUiState(
    val isLoading: Boolean = false,
    val listUsers: List<User> = emptyList()
)
