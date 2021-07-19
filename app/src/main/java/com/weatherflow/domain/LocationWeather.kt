package com.weatherflow.domain

import java.util.*

data class LocationWeather(
    val location: Location,
    val temp: Temp,
    val weather: Weather,
    val date: Date
)

data class Location(
    val name: String
)

data class Temp(
    val temp: Int,
    val minTemp: Int,
    val maxTemp: Int
)

data class Weather(
    val description: String
)
