package com.sangyoon.parkingpass.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sangyoon.parkingpass.repository.PassRepository
import kotlin.jvm.java

class ViewModelFactory(
    private val passRepository: PassRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PassListViewModel::class.java) -> {
                PassListViewModel(passRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}