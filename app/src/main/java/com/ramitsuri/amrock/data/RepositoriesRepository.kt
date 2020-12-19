package com.ramitsuri.amrock.data

import com.ramitsuri.amrock.api.ApiService
import com.ramitsuri.amrock.entities.RepositoryInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoriesRepository(private val api: ApiService) {

    private var cache = mutableListOf<RepositoryInfo>()

    fun getRepositories(): Flow<Result<List<RepositoryInfo>>> {
        return flow<Result<List<RepositoryInfo>>> {
            emit(Result.loading())
            if (cache.isEmpty()) {
                val response = api.getRepos()
                if (response.isSuccessful && response.body() != null) {
                    cache.addAll(response.body()!!)
                    emit(Result.success(cache))
                } else {
                    emit(Result.error(response.message()))
                }
            } else {
                emit(Result.success(cache))
            }
        }.catch {
            emit(Result.error("Network error!"))
        }.flowOn(Dispatchers.IO)
    }
}