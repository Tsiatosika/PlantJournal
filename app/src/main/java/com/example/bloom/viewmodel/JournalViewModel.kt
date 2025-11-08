package com.example.bloom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.data.repository.AuthRepository
import com.example.bloom.data.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JournalViewModel(
    private val plantRepository: PlantRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val discoveries: Flow<List<com.example.bloom.data.model.PlantDiscovery>>
        get() = plantRepository.getAllDiscoveriesByUser(authRepository.getCurrentUserId() ?: "")

    private val _uiState = MutableStateFlow<JournalUiState>(JournalUiState.Loading)
    val uiState: StateFlow<JournalUiState> = _uiState.asStateFlow()

    init {
        loadDiscoveries()
    }

    fun loadDiscoveries() {
        viewModelScope.launch {
            try {
                discoveries.collect { discoveriesList ->
                    _uiState.value = JournalUiState.Success(discoveriesList)
                }
            } catch (e: Exception) {
                _uiState.value = JournalUiState.Error("Erreur lors du chargement des découvertes: ${e.message}")
            }
        }
    }
}

sealed class JournalUiState {
    object Loading : JournalUiState()
    data class Success(val discoveries: List<com.example.bloom.data.model.PlantDiscovery>) : JournalUiState()
    data class Error(val message: String) : JournalUiState()
}