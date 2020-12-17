package com.ramitsuri.amrock.auth

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalAuthService(private val validator: AuthValidator) : AuthService {

    override suspend fun login(credentials: Credentials): Flow<AuthResult> {
        return flow {
            emit(AuthResult.Loading)
            delay(2000)
            emit(getAuthResult(credentials))
        }
    }

    private fun getAuthResult(credentials: Credentials): AuthResult {
        val isEmailValid = validator.isEmailValid(credentials.email)
        val isPasswordValid = validator.isPasswordValid(credentials.password)
        return if (isEmailValid && isPasswordValid) {
            AuthResult.Success
        } else if (isEmailValid) {
            AuthResult.PasswordError
        } else if (isPasswordValid) {
            AuthResult.EmailError
        } else {
            AuthResult.EmailPasswordError
        }
    }
}