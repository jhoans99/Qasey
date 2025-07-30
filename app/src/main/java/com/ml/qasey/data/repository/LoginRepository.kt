package com.ml.qasey.data.repository

import com.ml.qasey.data.datasource.user.LoginDataSource
import com.ml.qasey.model.Result
import com.ml.qasey.model.enums.LoginState
import com.ml.qasey.model.users.AccessData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val dataSource: LoginDataSource,
    private val userRepository: UserRepository
) {

    var uidUser: String = ""

     fun login(userName: String, password: String): Flow<Result<AccessData>> {
        return flow {
            emit(Result.Loading)
            dataSource.loginFirebase(userName, password).collect {
                when(it) {
                    LoginState.ERROR -> emit(Result.Error("Fallo generico del login"))
                    is LoginState.SUCCESS_LOGIN -> {
                        uidUser = it.uid
                        fetchUser(it.uid).collect { userAccessData ->
                            emit(Result.Success(userAccessData))
                        }
                    }
                }
            }
        }.catch {
            emit(Result.Error("Fallo generico del login"))
        }
    }

     fun fetchUser(userId: String): Flow<AccessData> = flow {
        userRepository.fetchUserRol(userId).collect {
            emit(it)
        }
    }
}