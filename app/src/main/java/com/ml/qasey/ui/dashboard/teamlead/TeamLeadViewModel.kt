package com.ml.qasey.ui.dashboard.teamlead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ml.qasey.data.repository.UserRepository
import com.ml.qasey.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamLeadViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(DashboardTeamLeadUiState())
    val uiState: StateFlow<DashboardTeamLeadUiState> = _uiState

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        viewModelScope.launch {
            userRepository.fetchAllUser().collect {
                when(it) {
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                    Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            listUsers = it.data
                        )
                    }
                }
            }
        }
    }

    fun changeStatusCustomer(id: String, status: Boolean) {
        viewModelScope.launch {
            userRepository.updateStatusCustomer(id,status).collect {
                when(it) {
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                    Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        getAllUsers()
                    }
                }
            }
        }
    }
}