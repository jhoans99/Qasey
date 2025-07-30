package com.ml.qasey.ui.dashboard.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ml.qasey.data.repository.CaseRepository
import com.ml.qasey.model.cases.CreateCase
import com.ml.qasey.model.Result
import com.ml.qasey.utils.DateUtils.getCurrentDate
import com.ml.qasey.utils.convertToFormatTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
            onShowModalTypeCase(true)
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
                    endDate = getCurrentDate()
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
                        getCasesUser()
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccessCreateCase = true
                        )
                    }
                }
            }
        }
    }

    fun getCasesUser() {
        viewModelScope.launch {
            caseRepository.fetchCaseByUser().collect {
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
                            historyCaseList = it.data
                        )
                    }
                }
            }
        }
    }

    fun onUpdateValueShowEditModal(value: Boolean) {
        _uiState.value = _uiState.value.copy(isShowModalEditCase = value)
    }

    fun saveNumberCaseToEdit(case: CreateCase) {
        _uiState.value = _uiState.value.copy(caseEdit = case)
    }

    fun updateCaseSelected(numberCase: String, idCase: String) {
        viewModelScope.launch {
            caseRepository.updateCaseByUser(id = idCase, numberCaseEdit = numberCase).collect {
                when(it) {
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                    Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        getCasesUser()
                    }
                }
            }
        }
    }

}

