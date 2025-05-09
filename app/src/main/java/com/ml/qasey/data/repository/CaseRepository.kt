package com.ml.qasey.data.repository

import com.ml.qasey.data.datasource.cases.CasesDataSource
import com.ml.qasey.model.CreateCase
import com.ml.qasey.model.Result
import com.ml.qasey.model.enums.CreateCaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CaseRepository @Inject constructor(
    private val casesDataSource: CasesDataSource
) {

    suspend fun createCase(createCase: CreateCase): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        casesDataSource.saveCase(createCase).collect {
            when(it) {
                CreateCaseState.CREATE_CASE_SUCCESS -> emit(Result.Success(Unit))
                CreateCaseState.ERROR -> emit(Result.Error("Error creando el caso"))
            }
        }
    }.catch {
        emit(Result.Error("Error creando el caso"))
    }
}