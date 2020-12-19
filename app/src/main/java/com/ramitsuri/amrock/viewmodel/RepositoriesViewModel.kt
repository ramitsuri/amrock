package com.ramitsuri.amrock.viewmodel

import androidx.lifecycle.ViewModel
import com.ramitsuri.amrock.data.RepositoriesRepository

class RepositoriesViewModel(private val repository: RepositoriesRepository) : ViewModel() {
    fun getRepositories() = repository.getRepositories()
}