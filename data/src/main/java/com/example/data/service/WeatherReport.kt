package com.example.data.service

data class WeatherReport(
    val date: Long,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
    val main: String,
    val description: String,
    val icon: String
)
