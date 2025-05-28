package com.ml.qasey.data.datasource.cases

import com.google.firebase.firestore.FirebaseFirestore
import com.ml.qasey.model.cases.CreateCase
import com.ml.qasey.model.cases.UpdateCase
import com.ml.qasey.model.enums.CreateCaseState
import com.ml.qasey.model.response.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CasesDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun saveCase(createCase: CreateCase, uidUser: String): Flow<CreateCaseState> {
        return callbackFlow {
            firestore.collection("Cases")
                .document(uidUser)
                .collection("casesByUser")
                .add(createCase)
                .addOnSuccessListener {
                    trySend(CreateCaseState.CREATE_CASE_SUCCESS)
                }
                .addOnFailureListener {
                    trySend(CreateCaseState.ERROR)
                }
            awaitClose {}
        }
    }

    suspend fun fetchCaseByUser(uidUser: String): Flow<List<CreateCase>> {
        return  callbackFlow {
            val listCaseUser: ArrayList<CreateCase> = ArrayList()
            firestore.collection("Cases")
                .document(uidUser)
                .collection("casesByUser")
                .get()
                .addOnSuccessListener {
                    for (document in it) {
                        val createCaseItem = CreateCase(
                            numberCase = document.data.get("numberCase").toString(),
                            endDate = document.data.get("endDate").toString(),
                            timer = document.data.get("timer").toString(),
                            typeCase = document.data.get("typeCase").toString(),
                            idCase = document.id
                        )
                        listCaseUser.add(createCaseItem)
                    }
                    trySend(listCaseUser)
                }
                .addOnFailureListener {

                }
            awaitClose {}
        }
    }

    suspend fun updateCase(updateCase: UpdateCase): Flow<Boolean> {
        return callbackFlow {
            val update = hashMapOf<String, Any>(
                "numberCase" to updateCase.numberCase
            )
            firestore.collection("Cases")
                .document(updateCase.userId)
                .collection("casesByUser")
                .document(updateCase.caseId)
                .update(update)
                .addOnSuccessListener {
                    trySend(true)
                }
                .addOnFailureListener {
                    trySend(false)
                }

            awaitClose {}
        }
    }
}