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
    private val timerJobs = mutableMapOf<String, Job>()

    //Use to UiState
    private val currentCasesByUser: ArrayList<String> = ArrayList()


    fun onValueChangeNumberCase(value: String) {
        if(value.length == 8 && currentCasesByUser.size < 4) {
            currentCasesByUser.add(value)
            startTimerForCase(value)
        } else if(currentCasesByUser.size == 4) {
            //Mostrar modal de que solo se puden agregar 4 casos
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
                    timer = "",
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
                        getCasesUser(it.data)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccessCreateCase = true
                        )
                        //Remove the currentCases list item to save
                    }
                }
            }
        }
    }

    fun getCasesUser(data: String) {
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
                        //getCasesUser(it.data)
                    }
                }
            }
        }
    }
    
    fun startTimerForCase(caseId: String) {
        // Si ya hay un timer corriendo para este caso, no crear otro
        if (timerJobs.containsKey(caseId)) return
        
        timerJobs[caseId] = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val currentTimers = _uiState.value.activeTimers.toMutableMap()
                currentTimers[caseId] = (currentTimers[caseId] ?: 0) + 1
                _uiState.value = _uiState.value.copy(activeTimers = currentTimers)
            }
        }
    }
    
    fun stopTimerForCase(caseId: String) {
        timerJobs[caseId]?.cancel()
        timerJobs.remove(caseId)
        //Active modal type Case
    }

}

