package com.ramitsuri.amrock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramitsuri.amrock.data.RepositoriesRepository

class RepositoriesViewModelFactory(
    private val repository: RepositoriesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RepositoriesRepository::class.java)
            .newInstance(repository)
    }
}