package com.ml.qasey.data.datasource.user

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.ml.qasey.model.enums.LoginState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun loginFirebase(userName: String, password: String): Flow<LoginState> {
        return callbackFlow {
            firebaseAuth.signInWithEmailAndPassword(
                userName,
                password
            ).addOnSuccessListener {
                trySend(LoginState.SUCCESS_LOGIN(it.user?.providerData?.get(0)?.uid ?: ""))
            }.addOnFailureListener {
                trySend(LoginState.ERROR)
            }
            awaitClose {  }
        }
    }
}