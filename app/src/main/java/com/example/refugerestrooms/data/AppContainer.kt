package com.example.refugerestrooms.data

import android.app.Application
import com.example.refugerestrooms.network.RestroomsApiService
import com.google.android.gms.location.LocationServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val restroomsRepository : RestroomsRepository
    val locationTracker : LocationTracker
}

class DefaultAppContainer(application: Application) : AppContainer {

    private val baseUrl =
        "https://www.refugerestrooms.org/api/"

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

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    override val locationTracker: LocationTracker by lazy {
        DefaultLocationTracker(fusedLocationProviderClient, application)
    }
}