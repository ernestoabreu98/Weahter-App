package com.example.weatherapp.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.WeatherReport
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.presentation.recyclerView.WeatherInfoAdapter
import com.example.weatherapp.presentation.utils.Constants
import com.example.weatherapp.presentation.utils.Data
import com.example.weatherapp.presentation.utils.Event
import com.example.weatherapp.presentation.utils.Status
import com.example.weatherapp.presentation.viewmodel.AppViewModelProvider
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel


class MainActivity : AppCompatActivity() {

    interface OnItemClickListener {
        fun onItemClick(dayReport: WeatherReport)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherInfoAdapter

    private val viewModel by lazy {
        AppViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        adapter = WeatherInfoAdapter(data.peekContent().data ?: emptyList(),
            object : OnItemClickListener {
                override fun onItemClick(dayReport: WeatherReport) {
                    Toast.makeText(this@MainActivity, Constants.ITEM_CLICKED, Toast.LENGTH_SHORT).show()
                }
            })
        binding.apply {
            recycler.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            recycler.adapter = adapter
        }
    }


    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showRequestError() {
        Toast.makeText(this, Constants.BAD_REQUEST, Toast.LENGTH_SHORT).show()
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}



