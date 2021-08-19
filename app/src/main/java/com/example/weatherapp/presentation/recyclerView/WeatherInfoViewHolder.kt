package com.example.weatherapp.presentation.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.RecyclerItemBinding

class WeatherInfoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = RecyclerItemBinding.bind(view)

    fun bind(title: String, image: String, subtitle: String){
        binding.apply {
            this.temperatureTexView.text = title
            Glide.with(this.weatherImageView.context).load(image).into(this.weatherImageView)
            this.dateTextView.text = subtitle
        }
    }
}