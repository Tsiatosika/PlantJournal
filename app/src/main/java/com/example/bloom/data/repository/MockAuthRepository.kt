package com.example.bloom.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockAuthRepository : AuthRepository {
    private val _isUserLoggedIn = MutableStateFlow(false)
    override val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    override fun getCurrentUserId(): String? {
        return if (_isUserLoggedIn.value) "mock_user_123" else null
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        _isUserLoggedIn.value = true
        return true
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        _isUserLoggedIn.value = true
        return true
    }

    override suspend fun signInWithGoogle(idToken: String): Boolean {
        _isUserLoggedIn.value = true
        return true
    }

    override fun signOut() {
        _isUserLoggedIn.value = false
    }
}