package com.example.domain.useCases

import com.example.domain.repositories.WeatherReportRepositoryContract

class GetWeatherReportUseCase {
    lateinit var weatherReportRepository: WeatherReportRepositoryContract
    suspend operator fun invoke() = weatherReportRepository.getWeatherReport()
}