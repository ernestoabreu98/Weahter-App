package com.example.weatherapp.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.mapper.Mapper
import com.example.data.service.WeatherService
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.presentation.recyclerView.WeatherInfoAdapter
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherInfoAdapter
    private val viewModel: WeatherViewModel by viewModels()

    private val mapper: Mapper = Mapper()
    private val client: WeatherService = WeatherService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = WeatherInfoAdapter(emptyList())
        binding.apply {
            this.recycler.layoutManager = GridLayoutManager(baseContext, 1)
            this.recycler.adapter = adapter
        }

        CoroutineScope(Dispatchers.Main).launch {
            adapter = withContext(Dispatchers.IO) {
                client.getWeatherInfo()?.let { WeatherInfoAdapter(it) }!!
            }
            binding.recycler.adapter = adapter
        }

        viewModel.oneCallResponse.observe(this, Observer {

        })
    }
}

