package com.ramitsuri.amrock.auth

import com.ramitsuri.amrock.entities.Credentials

class LocalAuthService(private val validator: AuthValidator) : AuthService {

    override suspend fun login(credentials: Credentials): AuthResult {
        return getAuthResult(credentials)
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