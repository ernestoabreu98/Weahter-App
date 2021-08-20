package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.WeatherReport
import com.example.domain.useCases.GetWeatherReportUseCase
import com.example.domain.utils.Result
import com.example.weatherapp.presentation.utils.Data
import com.example.weatherapp.presentation.utils.Event
import com.example.weatherapp.presentation.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val getWeatherReportUseCase: GetWeatherReportUseCase) : ViewModel() {

    private var mutableOneCallResponse: MutableLiveData<Event<Data<List<WeatherReport>>>> =
        MutableLiveData()
    val oneCallResponse: LiveData<Event<Data<List<WeatherReport>>>>
        get() {
            return mutableOneCallResponse
        }

    fun getWeatherReport() = viewModelScope.launch {
        mutableOneCallResponse.value = Event(Data(Status.LOADING))
        when (val result = withContext(Dispatchers.IO) { getWeatherReportUseCase() }) {
            is Result.Failure -> {
                mutableOneCallResponse.value =
                    Event(Data(responseType = Status.ERROR, error = result.exception))
            }
            is Result.Success -> {
                mutableOneCallResponse.value =
                    Event(Data(responseType = Status.SUCCESSFUL, data = result.data))
            }
        }
    }
}