package com.ml.qasey.ui.dashboard.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardCustomerViewModel @Inject constructor(): ViewModel() {

    private var _uiState = MutableStateFlow(DashboardCustomerUiState())
    val uiState: StateFlow<DashboardCustomerUiState> = _uiState

    private var job: Job? = null

    fun startTimer() {
        job = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _uiState.value = _uiState.value.copy(timer = _uiState.value.timer +1)
            }
        }
    }

    fun stopTimer() {
        job?.cancel()
    }

    fun resetTimer() {
        stopTimer()
        _uiState.value = _uiState.value.copy(timer = 0)
    }

    fun onValueChangeNumberCase(value: String) {
        if(value.length == 8) {
            startTimer()
        }
        _uiState.value = _uiState.value.copy(numberCase = value)
    }

}

