package com.example.domain.entities

data class WeatherReport(
    val date: Long,
    val temperature: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
    val title: String,
    val description: String,
    val icon: String
)
