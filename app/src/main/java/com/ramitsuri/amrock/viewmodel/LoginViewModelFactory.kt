package com.ramitsuri.amrock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramitsuri.amrock.auth.LoginManager
import com.ramitsuri.amrock.data.LoginRepository

class LoginViewModelFactory(
    private val loginRepository: LoginRepository,
    private val loginManager: LoginManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginRepository::class.java, LoginManager::class.java)
            .newInstance(loginRepository, loginManager)
    }
}