package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.WeatherReport
import com.example.data.service.WeatherService
import com.example.domain.useCases.GetWeatherReportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val getWeatherReportUseCase: GetWeatherReportUseCase): ViewModel() {

    private var mutableOneCallResponse: MutableLiveData<List<WeatherReport>> = MutableLiveData()
    val oneCallResponse: MutableLiveData<List<WeatherReport>>
        get() {
            return mutableOneCallResponse
        }
    init {
        onAppStarted()
    }

    private fun onAppStarted() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO){
            getWeatherReportUseCase()
        }
        mutableOneCallResponse.value = response
    }
}