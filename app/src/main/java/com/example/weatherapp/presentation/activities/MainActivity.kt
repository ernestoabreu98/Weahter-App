package com.example.weatherapp.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.mapper.Mapper
import com.example.data.repositories.WeatherReportRepository
import com.example.data.service.WeatherService
import com.example.domain.entities.WeatherReport
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.presentation.recyclerView.WeatherInfoAdapter
import com.example.weatherapp.presentation.viewmodel.AppViewModelProvider
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherInfoAdapter
    private val viewModel by lazy {
        AppViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = WeatherInfoAdapter(emptyList())
        binding.apply {
            this.recycler.layoutManager = GridLayoutManager(baseContext, 1)
            this.recycler.adapter = adapter
        }
        viewModel.oneCallResponse.observe(::getLifecycle, ::updateUI)
    }

    private fun updateUI(weatherData: List<WeatherReport>?) {
        if (weatherData?.isNotEmpty() == true){
            binding.apply {
                this.recycler.adapter = WeatherInfoAdapter(weatherData)
            }

        }

    }
}



