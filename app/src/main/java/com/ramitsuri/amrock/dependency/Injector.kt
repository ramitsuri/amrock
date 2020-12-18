package com.ramitsuri.amrock.dependency

import android.content.Context
import com.ramitsuri.amrock.auth.LocalAuthService
import com.ramitsuri.amrock.auth.LocalAuthValidator
import com.ramitsuri.amrock.auth.LoginManager
import com.ramitsuri.amrock.data.LoginRepository
import com.ramitsuri.amrock.viewmodel.LoginViewModelFactory

class Injector(context: Context) {
    private val authValidator = LocalAuthValidator()
    private val authService = LocalAuthService(authValidator)
    private val loginRepository = LoginRepository(authService)

    val loginManager = LoginManager(context)

    fun getLoginViewModelFactory() = LoginViewModelFactory(loginRepository, loginManager)
}