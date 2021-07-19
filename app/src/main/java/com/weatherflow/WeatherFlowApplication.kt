package com.weatherflow

import androidx.multidex.MultiDexApplication
import com.weatherflow.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherFlowApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WeatherFlowApplication)
            modules(appModule)
        }
    }
}