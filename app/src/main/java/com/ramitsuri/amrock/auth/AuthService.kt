package com.ramitsuri.amrock.auth

import com.ramitsuri.amrock.entities.Credentials

interface AuthService {
    suspend fun login(credentials: Credentials): AuthResult
}