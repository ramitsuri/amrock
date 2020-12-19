package com.ramitsuri.amrock.data

import com.ramitsuri.amrock.auth.AuthResult
import com.ramitsuri.amrock.auth.AuthService
import com.ramitsuri.amrock.entities.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository(private val authService: AuthService) {
    fun login(credentials: Credentials): Flow<Result<AuthResult>> {
        return flow<Result<AuthResult>> {
            emit(Result.loading())
            delay(2000)
            emit(Result.success(authService.login(credentials)))
        }.flowOn(Dispatchers.IO)
    }
}