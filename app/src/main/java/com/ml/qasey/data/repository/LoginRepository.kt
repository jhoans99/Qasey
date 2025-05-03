package com.ml.qasey.data.repository

import android.util.Printer
import com.ml.qasey.data.datasource.user.LoginDataSource
import com.ml.qasey.model.Result
import com.ml.qasey.model.enums.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val dataSource: LoginDataSource,
    private val userRepository: UserRepository
) {

    suspend fun login(userName: String, password: String): Flow<Result<String>> {
        return flow {
            emit(Result.Loading)
            dataSource.loginFirebase(userName, password).collect {
                when(it) {
                    LoginState.ERROR -> emit(Result.Error("Fallo generico del login"))
                    is LoginState.SUCCESS_LOGIN -> fetchUser(it.uid).collect { uid ->
                        emit(Result.Success(uid))
                    }
                }
            }
        }.catch {
            emit(Result.Error("Fallo generico del login"))
        }
    }

    private suspend fun fetchUser(userId: String): Flow<String> = flow {
        userRepository.fetchUserRol(userId).collect {
            emit(it)
        }
    }
}