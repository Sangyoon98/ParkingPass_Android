package com.sangyoon.parkingpass.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangyoon.parkingpass.model.Pass
import com.sangyoon.parkingpass.repository.PassRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PassListViewModel(
    val passRepository: PassRepository
): ViewModel() {
    private val _passList = MutableStateFlow<List<Pass>>((emptyList()))
    val passList: StateFlow<List<Pass>> = _passList

    init {
        viewModelScope.launch {
            passRepository.observePassList().collect { passList ->
                _passList.value = passList
            }
        }
    }

    fun addPass(pass: Pass) {
        _passList.value += pass
        viewModelScope.launch {
            passRepository.addPass(pass)
        }
    }
}