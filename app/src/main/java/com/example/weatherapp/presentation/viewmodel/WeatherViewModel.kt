package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.service.OneCallResponse
import com.example.data.service.WeatherReport
import com.example.data.service.WeatherService
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    private var mutableOneCallResponse: MutableLiveData<List<WeatherReport>> = MutableLiveData()
    val oneCallResponse: MutableLiveData<List<WeatherReport>>
        get() {
            return mutableOneCallResponse
        }
    init {
        onAppStarted()
    }

    private fun onAppStarted() = viewModelScope.launch {
        val client = WeatherService()
        val data = client.getWeatherInfo()
        println(data)
    }
}