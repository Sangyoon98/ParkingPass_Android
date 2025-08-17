package com.sangyoon.parkingpass.repository

import com.sangyoon.parkingpass.model.Pass
import kotlinx.coroutines.flow.Flow

interface PassRepository {
    fun observePassList(): Flow<List<Pass>>
    suspend fun addPass(pass: Pass)
}