package com.example.bloom.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.data.repository.AuthRepository
import com.example.bloom.data.repository.PlantRepository
import com.example.bloom.service.AIService
import com.example.bloom.utils.FileUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CaptureViewModel(
    private val plantRepository: PlantRepository,
    private val aiService: AIService,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _captureState = MutableStateFlow<CaptureState>(CaptureState.Idle)
    val captureState: StateFlow<CaptureState> = _captureState

    fun analyzeImage(imageUri: Uri, context: Context) {
        viewModelScope.launch {
            _captureState.value = CaptureState.Loading
            try {
                // Sauvegarder l'image localement
                val imagePath = FileUtils.saveImageToInternalStorage(imageUri, context)

                // Analyser avec l'IA
                val analysisResult = aiService.analyzePlantImage(imageUri)

                // Créer la découverte
                val discovery = com.example.bloom.data.model.PlantDiscovery(
                    userId = authRepository.getCurrentUserId() ?: "",
                    name = analysisResult.name,
                    summary = analysisResult.summary,
                    imagePath = imagePath
                )

                // Sauvegarder dans la base de données
                plantRepository.insertDiscovery(discovery)

                _captureState.value = CaptureState.Success
            } catch (e: Exception) {
                _captureState.value = CaptureState.Error("Erreur lors de l'analyse: ${e.message}")
            }
        }
    }

    fun resetState() {
        _captureState.value = CaptureState.Idle
    }
}

sealed class CaptureState {
    object Idle : CaptureState()
    object Loading : CaptureState()
    object Success : CaptureState()
    data class Error(val message: String) : CaptureState()
}