package com.weatherflow.repository

import com.weatherflow.BuildConfig
import com.weatherflow.api.WeatherService
import com.weatherflow.api.dto.toDomain
import com.weatherflow.domain.LocationWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val LAT = 53.13f
private const val LON = 50.13f

class RetrofitWeatherRepository(
    private val weatherService: WeatherService
) : WeatherRepository {

    override fun getWeather(): Flow<LocationWeather> {
        return flow {
            val dto = weatherService.getByCoordinates(LAT, LON, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
            emit(dto.toDomain())
        }.flowOn(Dispatchers.IO)
    }
}