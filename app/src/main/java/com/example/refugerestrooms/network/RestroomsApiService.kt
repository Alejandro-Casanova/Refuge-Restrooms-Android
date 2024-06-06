package com.example.refugerestrooms.network

import com.example.refugerestrooms.model.Restroom
import retrofit2.http.GET

interface RestroomsApiService {
    @GET("restrooms")
    suspend fun getRestrooms(): List<Restroom>
}