package com.example.weatherapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repositories.WeatherReportRepository
import com.example.data.service.WeatherService
import com.example.domain.useCases.GetWeatherReportUseCase

class AppViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == WeatherViewModel::class.java) {
            WeatherViewModel(GetWeatherReportUseCase().apply {
                weatherReportRepository = WeatherReportRepository(
                    WeatherService(),
                )
            }) as T
        } else {
            super.create(modelClass)
        }
    }

}