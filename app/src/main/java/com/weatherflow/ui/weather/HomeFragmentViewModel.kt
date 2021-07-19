package com.weatherflow.ui.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherflow.R
import com.weatherflow.domain.LocationWeather
import com.weatherflow.repository.WeatherRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.DateFormat

class HomeFragmentViewModel(
    private val application: Application,
    private val repository: WeatherRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Empty)
    val viewState: Flow<HomeViewState> = _viewState

    init {
        load()
    }

    fun refresh() = load(refresh = true)

    private fun load(refresh: Boolean = false) {
        viewModelScope.launch {
            repository.getWeather()
                .map<LocationWeather, HomeViewState> {
                    val temp = application.getString(R.string.temp_template, it.temp.temp)
                    val minMaxTemp = application.getString(R.string.min_max_temp_template, it.temp.minTemp, it.temp.maxTemp)
                    val date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(it.date)
                    HomeViewState.Content(it.location.name, temp, minMaxTemp, date, it.weather.description)
                }
                .onStart { emit(if (refresh) HomeViewState.Refreshing else HomeViewState.Loading) }
                .catch { emit(HomeViewState.Error(it.message ?: application.getString(R.string.unknown_error))) }
                .collect { _viewState.value = it }
        }
    }
}

sealed class HomeViewState {

    object Empty : HomeViewState()

    object Loading : HomeViewState()

    object Refreshing : HomeViewState()

    data class Content(
        val locationName: String,
        val temp: String,
        val tempMinMax: String,
        val dateTime: String,
        val description: String
    ) : HomeViewState()

    data class Error(val message: String) : HomeViewState()
}