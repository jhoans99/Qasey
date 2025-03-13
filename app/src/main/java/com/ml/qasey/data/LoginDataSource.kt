package com.ml.qasey.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun loginFirebase(userName: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(
            userName,
            password
        ).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                //Login Exitoso
                Log.d("TAG-----", "login success: ")
            } else {
                //Login Fallido
                Log.d("TAG-----", "login failure: ")
            }
        }
    }
}