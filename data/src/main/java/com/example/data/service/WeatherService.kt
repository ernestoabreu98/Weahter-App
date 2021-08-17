package com.example.data.service

import com.example.data.mapper.BaseMapper
import com.example.data.mapper.Mapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class WeatherService {

    companion object {

        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    }

    private val mapper: Mapper = Mapper()

    private fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    suspend fun getWeatherInfo(): List<WeatherReport>? {
        var data: List<WeatherReport>? = null
        val response = getRetrofit().create(WeatherAPI::class.java).getWeatherInfo()
        if (response.isSuccessful) {
            response.body()?.let {
                data = mapper.transform(it)
            }
        }
        return data
    }
}