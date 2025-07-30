package com.ml.qasey.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import com.ml.qasey.data.datasource.user.UserDataSource
import com.ml.qasey.model.Result
import com.ml.qasey.model.response.User
import com.ml.qasey.model.users.AccessData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSource: UserDataSource
) {

     fun fetchUserRol(userId: String): Flow<AccessData> {
        return callbackFlow {
            dataSource.fetchUserById(userId).collect {
                trySend(
                    AccessData(
                        rol = it?.Rol ?: "",
                        isEnabled = it?.isEnabled ?: false
                    )
                )
            }
            awaitClose {  }
        }
    }

    fun fetchAllUser(): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        dataSource.fetchAllUsers().collect {
            emit(Result.Success(it))
        }
    }.catch {
        emit(Result.Error("Error al obtener los customer"))
    }

    fun updateStatusCustomer(userId: String, enabled: Boolean): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        dataSource.updateStatusCustomer(userId,enabled).collect {
            when(it) {
                true -> emit(Result.Success(Unit))
                false -> emit(Result.Error("Hubo un error al actualizar el estado"))
            }
        }
    }.catch {
        emit(Result.Error("Hubo un error al actualizar el estado"))
    }
}