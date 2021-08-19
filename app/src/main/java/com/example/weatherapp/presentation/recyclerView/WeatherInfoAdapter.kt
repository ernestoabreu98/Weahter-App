package com.example.weatherapp.presentation.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.WeatherReport
import com.example.weatherapp.R

class WeatherInfoAdapter(private val weatherReport: List<WeatherReport>): RecyclerView.Adapter<WeatherInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WeatherInfoViewHolder(layoutInflater.inflate(R.layout.recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: WeatherInfoViewHolder, position: Int) {
        val weatherData = weatherReport[position]
        val itemTitle = convertToCelsius(weatherData.temperature)
        val itemImage = "https://openweathermap.org/img/wn/${weatherData.icon}.png"
        val itemSubtitle = weatherData.date.toString()
        holder.bind(itemTitle, itemImage, itemSubtitle)
    }

    override fun getItemCount(): Int = weatherReport.size

    private fun convertToCelsius(temp: Double) : String {
        return "${String.format("%.1f", temp)}°C"
    }
}