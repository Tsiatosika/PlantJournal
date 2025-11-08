package com.example.bloom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.data.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {

    fun getDiscovery(discoveryId: Long): Flow<com.example.bloom.data.model.PlantDiscovery?> {
        return plantRepository.getDiscoveryByIdFlow(discoveryId)
    }

    fun deleteDiscovery(discovery: com.example.bloom.data.model.PlantDiscovery) {
        viewModelScope.launch {
            plantRepository.deleteDiscovery(discovery)
        }
    }
}