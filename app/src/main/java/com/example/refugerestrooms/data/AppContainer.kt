package com.example.refugerestrooms.data

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.refugerestrooms.network.RestroomsApiService
import com.google.android.gms.location.LocationServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val restroomsRepository : RestroomsRepository
    val locationTracker : LocationTracker
    val userPreferencesRepository: UserPreferencesRepository
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

    private val layoutPreferenceName = "layout_preferences"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = layoutPreferenceName
    )

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(application.applicationContext.dataStore)
    }
}


