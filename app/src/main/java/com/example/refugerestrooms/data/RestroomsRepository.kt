package com.example.refugerestrooms.data

import com.example.refugerestrooms.model.Restroom
import com.example.refugerestrooms.network.RestroomsApiService

interface RestroomsRepository {
    suspend fun getRestrooms(): List<Restroom>
    suspend fun getRestroomsByLocation(latitude: Double, longitude: Double): List<Restroom>
    suspend fun getRestroomsByQuery(query: String): List<Restroom>
}

class NetworkRestroomsRepository(
    private val restroomApiService: RestroomsApiService
) : RestroomsRepository {
    override suspend fun getRestrooms(): List<Restroom>  {
        return restroomApiService.getRestrooms()
    }

    override suspend fun getRestroomsByLocation(latitude: Double, longitude: Double): List<Restroom>  {
        return restroomApiService.getRestroomsByLocation(
            latitude = latitude,
            longitude = longitude,
            perPage = 10 // TODO: handle progressive requesting of new restrooms as list is scrolled
        )
    }

    override suspend fun getRestroomsByQuery(query: String): List<Restroom> {
        return restroomApiService.getRestroomsByQuery(
            query = query,
            perPage = 10 // TODO: handle progressive requesting of new restrooms as list is scrolled
        )
    }
}