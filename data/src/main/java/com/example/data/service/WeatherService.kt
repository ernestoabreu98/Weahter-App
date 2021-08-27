package com.example.data.service

import com.example.data.mapper.Mapper
import com.example.domain.entities.WeatherReport
import com.example.domain.utils.Result
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class WeatherService {

    companion object {

        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val CONNECT_TIMEOUT: Long = 1
        private const val READ_TIMEOUT: Long = 30
        private const val WRITE_TIMEOUT: Long = 15

    }

    private val mapper = Mapper()

    private fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    suspend fun getWeatherInfo(): Result<List<WeatherReport>> {
        var data: List<WeatherReport> = emptyList()
        return try {
            val response = getRetrofit().create(WeatherAPI::class.java).getWeatherInfo()
            if (response.isSuccessful) {
                response.body()?.let {
                    data = mapper.transform(it)
                }
                return Result.Success(data)
            }
            Result.Failure(Exception(response.message()))
        } catch (e: RuntimeException) {
            Result.Failure(java.lang.Exception("Bad request/response"))
        } catch (e: IOException) {
            Result.Failure(java.lang.Exception("Bad request/response"))
        }
    }
}