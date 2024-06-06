package com.example.refugerestrooms.data

import com.example.refugerestrooms.network.RestroomsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val restroomsRepository : RestroomsRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl =
        "https://www.refugerestrooms.org/api/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: RestroomsApiService by lazy {
        retrofit.create(RestroomsApiService::class.java)
    }
    override val restroomsRepository: RestroomsRepository by lazy {
        NetworkRestroomsRepository(retrofitService)
    }
}