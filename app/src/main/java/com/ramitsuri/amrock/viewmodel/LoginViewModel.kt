package com.ramitsuri.amrock.viewmodel

import androidx.lifecycle.ViewModel
import com.ramitsuri.amrock.auth.AuthResult
import com.ramitsuri.amrock.entities.Credentials
import com.ramitsuri.amrock.auth.LoginManager
import com.ramitsuri.amrock.data.LoginRepository
import com.ramitsuri.amrock.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class LoginViewModel(
    private val repository: LoginRepository,
    private val loginManager: LoginManager
) : ViewModel() {

    fun login(email: String, password: String): Flow<Result<AuthResult>> {
        val credentials = Credentials(email, password)
        return repository.login(credentials).onEach { result ->
            if (result is Result.Success) {
                when (result.data) {
                    AuthResult.Success -> {
                        onLoginSuccess(credentials)
                    }
                    else -> {
                        onLoginFailure(Credentials("", ""))
                    }
                }
            }
        }
    }

    fun getEmail(): String {
        return loginManager.getCredentials().email
    }

    fun getPassword(): String {
        return loginManager.getCredentials().password
    }

    private fun onLoginSuccess(credentials: Credentials) {
        loginManager.setLoggedIn(true)
        loginManager.setCredentials(credentials)
    }

    private fun onLoginFailure(credentials: Credentials) {
        loginManager.setCredentials(credentials)
    }
}