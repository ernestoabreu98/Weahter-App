package com.example.weatherapp.presentation.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.RecyclerItemBinding

class WeatherInfoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = RecyclerItemBinding.bind(view)

    fun bind(title: String, image: String){
        binding.apply {
            this.temperature.text = title
        }
    }
}