package com.example.bloom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val success = authRepository.signIn(email, password)
                _authState.value = if (success) AuthState.Success else AuthState.Error("Échec de la connexion")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Erreur: ${e.message}")
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val success = authRepository.signUp(email, password)
                _authState.value = if (success) AuthState.Success else AuthState.Error("Échec de l'inscription")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Erreur: ${e.message}")
            }
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // Ici vous devriez lancer l'intent de connexion Google
                // et récupérer le idToken depuis l'activité
                val idToken = "" // À récupérer depuis l'UI
                val success = authRepository.signInWithGoogle(idToken)
                _authState.value = if (success) AuthState.Success else AuthState.Error("Échec de la connexion Google")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Erreur: ${e.message}")
            }
        }
    }

    // Méthode pour traiter le résultat
    fun handleGoogleSignInResult(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val success = authRepository.signInWithGoogle(idToken)
                _authState.value = if (success) AuthState.Success else AuthState.Error("Échec de la connexion Google")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Erreur: ${e.message}")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        object Success : AuthState()
        data class Error(val message: String) : AuthState()
    }
}