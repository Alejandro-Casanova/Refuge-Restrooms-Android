package com.example.refugerestrooms.network

import com.example.refugerestrooms.model.Restroom
import retrofit2.http.GET
import retrofit2.http.Query

const val API_VERSION = "v1"
interface RestroomsApiService {
    @GET("${API_VERSION}/restrooms")
    suspend fun getRestrooms(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("ada") accessible: Boolean = false,
        @Query("unisex") unisex: Boolean = false,
    ): List<Restroom>

    @GET("${API_VERSION}/restrooms")
    suspend fun getRestroomsByLocation(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("ada") accessible: Boolean = false,
        @Query("unisex") unisex: Boolean = false,
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
    ): List<Restroom>
}