package com.ml.qasey.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun loginFirebase(userName: String, password: String): Flow<Boolean> {
        return callbackFlow {
            firebaseAuth.signInWithEmailAndPassword(
                userName,
                password
            ).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    trySend(true)
                } else {
                    trySend(false)
                }
            }
            awaitClose {  }
        }
    }
}