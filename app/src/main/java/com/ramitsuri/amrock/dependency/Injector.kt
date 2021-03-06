package com.ramitsuri.amrock.dependency

import android.content.Context
import com.ramitsuri.amrock.api.ApiService
import com.ramitsuri.amrock.auth.LocalAuthService
import com.ramitsuri.amrock.auth.LocalAuthValidator
import com.ramitsuri.amrock.auth.LoginManager
import com.ramitsuri.amrock.credential.PrefsCredentialStorage
import com.ramitsuri.amrock.data.LoginRepository
import com.ramitsuri.amrock.data.RepositoriesRepository
import com.ramitsuri.amrock.utils.DateTimeHelper
import com.ramitsuri.amrock.viewmodel.LoginViewModelFactory
import com.ramitsuri.amrock.viewmodel.RepositoriesViewModelFactory
import java.time.ZoneId
import java.util.*

class Injector(context: Context) {
    private val loginRepository = LoginRepository(LocalAuthService(LocalAuthValidator()))
    private val repositoriesRepository =
        RepositoriesRepository(ApiService.create(getDateTimeHelper()))
    val loginManager = LoginManager(PrefsCredentialStorage(context))

    fun getLoginViewModelFactory() = LoginViewModelFactory(loginRepository, loginManager)
    fun getRepositoriesViewModelFactory() =
        RepositoriesViewModelFactory(repositoriesRepository, loginManager)
    fun getDateTimeHelper() = DateTimeHelper(ZoneId.systemDefault(), Locale.getDefault())
}