package com.ramitsuri.amrock.api

import com.google.gson.GsonBuilder
import com.ramitsuri.amrock.entities.RepositoryInfo
import com.ramitsuri.amrock.serializer.InstantAdapter
import com.ramitsuri.amrock.utils.DateTimeHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.time.Instant

interface ApiService {
    @GET("repos")
    suspend fun getRepos(): Response<List<RepositoryInfo>>

    companion object {
        private const val BASE_URL = "https://api.github.com/users/QuickenLoans/"

        fun create(dateTimeHelper: DateTimeHelper): ApiService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val gson = GsonBuilder()
                .registerTypeAdapter(
                    Instant::class.java,
                    InstantAdapter(dateTimeHelper)
                )
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }


    }
}