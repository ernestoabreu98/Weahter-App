package com.example.data.repositories

import com.example.data.mapper.Mapper
import com.example.domain.entities.WeatherReport
import com.example.data.service.WeatherService
import com.example.domain.repositories.WeatherReportRepositoryContract

class WeatherReportRepository(private val weatherService: WeatherService):
    WeatherReportRepositoryContract {

    private val mapper = Mapper()
    override suspend fun getWeatherReport(): List<WeatherReport>? {
        return weatherService.getWeatherInfo()?.let { mapper.transform(it) }
    }
}