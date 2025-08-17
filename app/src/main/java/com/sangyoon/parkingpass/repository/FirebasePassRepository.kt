package com.sangyoon.parkingpass.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.sangyoon.parkingpass.model.Pass
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebasePassRepository : PassRepository {
    val db = Firebase.firestore

    override fun observePassList(): Flow<List<Pass>> = callbackFlow {
        val listener = db.collection("pass")
            .orderBy("timestampMillis", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("FirebasePassRepository", "Error fetching pass list", error)
                    close(error)
                } else if (snapshot != null) {
                    val passList = snapshot.toObjects(Pass::class.java)
                    trySend(passList)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addPass(pass: Pass) {
        try {
            val passMap = hashMapOf(
                "id" to pass.id,
                "timestampMillis" to pass.timestampMillis,
                "direction" to pass.direction,
                "gateId" to pass.gateId,
                "plateNumber" to pass.plateNumber,
                "confidence" to pass.confidence,
                "imageUrl" to pass.imageUrl,
                "source" to pass.source,
                "carId" to pass.carId
            )
            db.collection("pass")
                .document(pass.id)
                .set(passMap)
                .await()
        } catch (e: Exception) {
            Log.e("FirebasePassRepository", "Error adding pass", e)
        }
    }
}