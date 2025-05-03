package com.ml.qasey.data.datasource.user

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ml.qasey.model.response.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun fetchUserById(userId: String): Flow<User?> {
        return callbackFlow {
            firestore
                .collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener {
                    if(it.exists()) {
                        trySend(it.toObject(User::class.java))
                    }
                }
                .addOnFailureListener {

                }
            awaitClose {  }
        }
    }
}