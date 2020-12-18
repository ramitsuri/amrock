package com.ramitsuri.amrock.data

import com.ramitsuri.amrock.auth.AuthResult
import com.ramitsuri.amrock.auth.AuthService
import com.ramitsuri.amrock.auth.Credentials
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository(private val authService: AuthService) {
    fun login(credentials: Credentials): Flow<AuthResult> {
        return flow {
            emit(AuthResult.Loading)
            delay(2000)
            emit(authService.login(credentials))
        }
    }
}