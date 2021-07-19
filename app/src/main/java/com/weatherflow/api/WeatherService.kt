package com.weatherflow.api

import com.weatherflow.api.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getByCoordinates(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") appId: String
    ): WeatherResponseDto
}