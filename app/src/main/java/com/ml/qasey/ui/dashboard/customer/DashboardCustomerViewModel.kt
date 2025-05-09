package com.ml.qasey.ui.dashboard.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ml.qasey.data.repository.CaseRepository
import com.ml.qasey.model.CreateCase
import com.ml.qasey.model.Result
import com.ml.qasey.utils.convertToFormatTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DashboardCustomerViewModel @Inject constructor(
    private val caseRepository: CaseRepository
): ViewModel() {

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

    fun onShowModalTypeCase(value: Boolean) {
        _uiState.value = _uiState.value.copy(isShowModalTypeCase = value)
    }

    fun saveCase(
        typeCase: String
    ) {
        viewModelScope.launch {
            caseRepository.createCase(
                CreateCase(
                    numberCase = _uiState.value.numberCase,
                    timer = _uiState.value.timer.convertToFormatTime(),
                    typeCase = typeCase,
                    endDate = LocalDate.now().toString()
                )
            ).collect {
                when(it) {
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isFailureCreateCase = true
                        )
                    }
                    Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccessCreateCase = true
                        )
                    }
                }
            }
        }
    }

}

