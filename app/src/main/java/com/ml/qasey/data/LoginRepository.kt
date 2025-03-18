package com.ml.qasey.data

import com.ml.qasey.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val dataSource: LoginDataSource
) {

    suspend fun login(userName: String, password: String): Flow<Result<Boolean>> {
        return flow {
            emit(Result.Loading)
            dataSource.loginFirebase(userName, password).collect {
                emit(Result.Success(it))
            }
        }.catch {
            emit(Result.Error("Fallo generico del login"))
        }
    }
}