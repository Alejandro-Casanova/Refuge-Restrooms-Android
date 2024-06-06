package com.example.refugerestrooms.data

import com.example.refugerestrooms.model.Restroom
import com.example.refugerestrooms.network.RestroomsApiService

interface RestroomsRepository {
    suspend fun getRestrooms(): List<Restroom>
}

class NetworkRestroomsRepository(
    private val restroomApiService: RestroomsApiService
) : RestroomsRepository {
    override suspend fun getRestrooms(): List<Restroom>  {
        return restroomApiService.getRestrooms()
    }
}