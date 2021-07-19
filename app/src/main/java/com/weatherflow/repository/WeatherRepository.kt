package com.weatherflow.repository

import com.weatherflow.domain.LocationWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(): Flow<LocationWeather>
}