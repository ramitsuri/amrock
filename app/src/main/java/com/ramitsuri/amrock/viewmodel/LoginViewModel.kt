package com.ramitsuri.amrock.viewmodel

import androidx.lifecycle.ViewModel
import com.ramitsuri.amrock.auth.AuthResult
import com.ramitsuri.amrock.auth.Credentials
import com.ramitsuri.amrock.auth.LoginManager
import com.ramitsuri.amrock.data.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class LoginViewModel(
    private val repository: LoginRepository,
    private val loginManager: LoginManager
) : ViewModel() {

    fun login(email: String, password: String): Flow<AuthResult> {
        val credentials = Credentials(email, password)
        return repository.login(credentials).onEach { authResult ->
            if (authResult == AuthResult.Success) {
                onLoginSuccess(credentials)
            } else if (authResult != AuthResult.Loading) {
                onLoginFailure(Credentials("", ""))
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