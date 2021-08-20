package com.example.domain.repositories

import com.example.domain.entities.WeatherReport
import com.example.domain.utils.Result

interface WeatherReportRepositoryContract {

    suspend fun getWeatherReport(): Result<List<WeatherReport>>
}
