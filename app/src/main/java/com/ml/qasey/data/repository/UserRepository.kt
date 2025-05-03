package com.ml.qasey.data.repository

import com.ml.qasey.data.datasource.user.UserDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSource: UserDataSource
) {

    suspend fun fetchUserData() {
    }

    suspend fun fetchUserRol(userId: String): Flow<String> {
        return callbackFlow {
            dataSource.fetchUserById(userId).collect {
                trySend(it?.Rol ?: "")
            }
            awaitClose {  }
        }
    }
}