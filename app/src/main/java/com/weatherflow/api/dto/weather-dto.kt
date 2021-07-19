package com.weatherflow.api.dto

import com.squareup.moshi.Json
import com.weatherflow.domain.Location
import com.weatherflow.domain.LocationWeather
import com.weatherflow.domain.Temp
import com.weatherflow.domain.Weather
import java.util.*
import kotlin.math.roundToInt

data class WeatherResponseDto(
    val coord: CoordDto,
    val weather: List<WeatherDto>,
    val main: MainDto,
    val visibility: Long,
    val wind: WindDto,
    val clouds: CloudsDto,
    val name: String,
    val dt: Long
)

data class CoordDto(
    val lon: Float,
    val lat: Float
)

data class WeatherDto(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class MainDto(
    val temp: Float,
    @Json(name = "feels_like") val feelsLike: Float,
    @Json(name = "temp_min") val tempMin: Float,
    @Json(name = "temp_max") val tempMax: Float,
    val pressure: Int,
    val humidity: Int
)

data class WindDto(
    val speed: Float,
    val deg: Int
)

data class CloudsDto(
    val all: Int
)

fun WeatherResponseDto.toDomain(): LocationWeather {
    return LocationWeather(
        Location(name),
        Temp(main.temp.roundToInt(), main.tempMin.roundToInt(), main.tempMax.roundToInt()),
        Weather(weather.first().description),
        Date(dt)
    )
}