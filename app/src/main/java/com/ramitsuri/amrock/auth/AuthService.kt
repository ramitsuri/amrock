package com.ramitsuri.amrock.auth

import kotlinx.coroutines.flow.Flow

interface AuthService {
    suspend fun login(credentials: Credentials): Flow<AuthResult>
}