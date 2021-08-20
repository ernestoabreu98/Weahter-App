package com.example.data.repositories

import com.example.data.service.WeatherService
import com.example.domain.entities.WeatherReport
import com.example.domain.repositories.WeatherReportRepositoryContract
import com.example.domain.utils.Result

class WeatherReportRepository(private val weatherService: WeatherService) :
    WeatherReportRepositoryContract {

    override suspend fun getWeatherReport(): Result<List<WeatherReport>> {
        return weatherService.getWeatherInfo()
    }
}