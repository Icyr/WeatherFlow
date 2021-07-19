package com.weatherflow.ui.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.weatherflow.R
import com.weatherflow.domain.LocationWeather
import com.weatherflow.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.text.DateFormat

class HomeFragmentViewModel(
    application: Application,
    repository: WeatherRepository
) : AndroidViewModel(application) {

    val viewState: Flow<HomeViewState> = repository.getWeather()
        .map<LocationWeather, HomeViewState> {
            val temp = application.getString(R.string.temp_template, it.temp.temp)
            val minMaxTemp = application.getString(R.string.min_max_temp_template, it.temp.minTemp, it.temp.maxTemp)
            val date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(it.date)
            HomeViewState.Content(it.location.name, temp, minMaxTemp, date, it.weather.description)
        }
        .onStart { emit(HomeViewState.Loading) }
        .catch { emit(HomeViewState.Error(it.message ?: application.getString(R.string.unknown_error))) }
}

sealed class HomeViewState {

    object Loading : HomeViewState()

    data class Content(
        val locationName: String,
        val temp: String,
        val tempMinMax: String,
        val dateTime: String,
        val description: String
    ) : HomeViewState()

    data class Error(val message: String) : HomeViewState()
}