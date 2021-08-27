package com.example.weatherapp.presentation.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.WeatherReport
import com.example.weatherapp.R
import com.example.weatherapp.presentation.activities.MainActivity
import com.example.weatherapp.presentation.utils.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherInfoAdapter(
    private val weatherReport: List<WeatherReport>,
    private val onItemClickListener: MainActivity.OnItemClickListener
) :
    RecyclerView.Adapter<WeatherInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WeatherInfoViewHolder(layoutInflater.inflate(R.layout.recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: WeatherInfoViewHolder, position: Int) {
        val weatherData = weatherReport[position]
        val itemTitle = convertToCelsius(weatherData.temperature)
        val itemImage = Constants.API_ICON_BASE_URL + weatherData.icon + Constants.ICON_FORMAT
        val itemSubtitle =
            SimpleDateFormat(Constants.DATE_FORMAT_PATTERN, Locale.getDefault()).format(
                Date(weatherData.date * 1000)
            )
        holder.bind(itemTitle, itemImage, itemSubtitle)

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(weatherData)
        }
    }

    override fun getItemCount(): Int = weatherReport.size

    private fun convertToCelsius(temp: Double): String {
        return String.format(Constants.DECIMAL_SPACES, temp) + Constants.CELSIUS_IDENTIFIER
    }
}