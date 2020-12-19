package com.ramitsuri.amrock.data

import com.ramitsuri.amrock.api.ApiService
import com.ramitsuri.amrock.entities.RepositoryInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoriesRepository(private val api: ApiService) {

    private var cache = mutableListOf<RepositoryInfo>()

    fun getRepositories(): Flow<List<RepositoryInfo>> {
        return flow {
            if (cache.isEmpty()) {
                cache.addAll(api.getRepos())
            }
            emit(cache)
        }.flowOn(Dispatchers.IO)
    }
}