package com.example.domain.repositories

import com.example.domain.entities.WeatherReport

interface WeatherReportRepositoryContract {

    suspend fun getWeatherReport(): List<WeatherReport>?
}
