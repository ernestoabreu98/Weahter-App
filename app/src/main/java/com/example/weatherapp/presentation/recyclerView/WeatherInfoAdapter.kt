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
        val itemTitle = weatherData.date.toString()
        val itemImage = weatherData.icon
        holder.bind(itemTitle, itemImage)
    }

    override fun getItemCount(): Int = weatherReport.size

}