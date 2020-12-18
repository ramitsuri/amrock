package com.ramitsuri.amrock.auth

interface AuthService {
    suspend fun login(credentials: Credentials): AuthResult
}