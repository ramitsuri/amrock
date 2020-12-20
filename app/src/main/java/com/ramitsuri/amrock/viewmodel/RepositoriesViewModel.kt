package com.ramitsuri.amrock.viewmodel

import androidx.lifecycle.ViewModel
import com.ramitsuri.amrock.auth.LoginManager
import com.ramitsuri.amrock.data.RepositoriesRepository

class RepositoriesViewModel(
    private val repository: RepositoriesRepository,
    private val loginManager: LoginManager
) : ViewModel() {

    fun getRepositories() = repository.getRepositories()

    fun onLogout() {
        loginManager.setLoggedIn(false)
    }
}