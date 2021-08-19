package com.example.data.mapper

import com.example.data.service.OneCallResponse
import com.example.domain.entities.WeatherReport

open class Mapper : BaseMapper<OneCallResponse, List<WeatherReport>> {

    override fun transform(type: OneCallResponse): List<WeatherReport> {
        return type.daily.map { daily ->
            val weather = daily.weather.first()
            WeatherReport(
                date = daily.date,
                minTemp = daily.temp.min,
                maxTemp = daily.temp.max,
                title = weather.main,
                description = weather.description,
                humidity = daily.humidity,
                icon = weather.icon
            )
        }
    }
}