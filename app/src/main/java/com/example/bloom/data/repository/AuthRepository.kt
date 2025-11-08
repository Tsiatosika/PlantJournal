package com.example.bloom.data.repository

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val isUserLoggedIn: StateFlow<Boolean>
    fun getCurrentUserId(): String?
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signInWithGoogle(idToken: String): Boolean
    fun signOut()
}