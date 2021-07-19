package com.weatherflow.di

import com.weatherflow.api.WeatherService
import com.weatherflow.repository.RetrofitWeatherRepository
import com.weatherflow.repository.WeatherRepository
import com.weatherflow.ui.weather.HomeFragmentViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://api.openweathermap.org/data/2.5/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

val appModule = module {

    single { retrofit.create<WeatherService>() }

    factory<WeatherRepository> { RetrofitWeatherRepository(get()) }

    viewModel { HomeFragmentViewModel(androidApplication(), get()) }

}