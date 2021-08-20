package com.example.weatherapp.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.entities.WeatherReport
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.presentation.recyclerView.WeatherInfoAdapter
import com.example.weatherapp.presentation.utils.Data
import com.example.weatherapp.presentation.utils.Event
import com.example.weatherapp.presentation.utils.Status
import com.example.weatherapp.presentation.viewmodel.AppViewModelProvider
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel


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
        viewModel.getWeatherReport()
    }

    private fun updateUI(weatherData: Event<Data<List<WeatherReport>>>) {
        when (weatherData.peekContent().responseType) {
            Status.LOADING -> {
                showProgressBar()
            }

            Status.ERROR -> {
                hideProgressBar()
                showRequestError()
            }

            Status.SUCCESSFUL -> {
                hideProgressBar()
                fillRecyclerView(weatherData)
            }
        }
    }

    private fun fillRecyclerView(data: Event<Data<List<WeatherReport>>>) {
        adapter = data.peekContent().data?.let {
            WeatherInfoAdapter(it)
        }!!
        binding.recycler.adapter = adapter
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showRequestError() {
        Toast.makeText(this, "Request Fail. Try Again", Toast.LENGTH_SHORT).show()
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}



