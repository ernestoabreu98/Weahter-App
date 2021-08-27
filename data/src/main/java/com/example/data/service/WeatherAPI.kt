package com.example.data.service

import retrofit2.Response
import retrofit2.http.GET

interface WeatherAPI {

    companion object {
        private const val API_KEY = "d92f2b857e851f5da69104944c170d57"
        private const val EXCLUDE_FROM_REQUEST = "hourly,minutely"
        private const val UNITS = "metric"
        private const val LAT = "lat=33.44"
        private const val LON = "lon=-94.04"
    }

    @GET("onecall?${LAT}&${LON}&units=${UNITS}&exclude=${EXCLUDE_FROM_REQUEST}&appid=${API_KEY}")
    suspend fun getWeatherInfo(): Response<OneCallResponse>
}