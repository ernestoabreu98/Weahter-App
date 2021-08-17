package com.example.data.service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class OneCallResponse(
    @Json(name = "daily") val daily: List<OneCallDailyResponse>,
    @Json(name = "lat") val lat: Double,
    @Json(name = "lon") val lon: Double,
): Serializable

@JsonClass(generateAdapter = true)
data class OneCallDailyResponse(
    @Json(name = "dt") val date: Long,
    @Json(name = "temp") val temp: OneCallTempResponse,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "weather") val weather: List<OneCallWeatherResponse>,
): Serializable

@JsonClass(generateAdapter = true)
data class OneCallTempResponse(
    @Json(name = "min") val min: Double,
    @Json(name = "max") val max: Double
): Serializable

@JsonClass(generateAdapter = true)
data class OneCallWeatherResponse (
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
): Serializable
