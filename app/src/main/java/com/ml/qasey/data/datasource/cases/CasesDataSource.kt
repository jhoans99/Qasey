package com.ml.qasey.data.datasource.cases

import com.google.firebase.firestore.FirebaseFirestore
import com.ml.qasey.model.CreateCase
import com.ml.qasey.model.enums.CreateCaseState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CasesDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun saveCase(createCase: CreateCase): Flow<CreateCaseState> {
        return callbackFlow {
            firestore.collection("Cases")
                .document("SWx0DVkJKoOpdZPzGoz5PK8TuFy2")
                .collection(createCase.numberCase)
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
}