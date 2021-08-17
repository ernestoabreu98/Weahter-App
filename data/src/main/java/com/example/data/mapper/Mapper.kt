package com.example.data.mapper

import com.example.data.service.OneCallResponse
import com.example.data.service.WeatherReport

open class Mapper: BaseMapper<OneCallResponse, List<WeatherReport>> {

    override fun transform(type: OneCallResponse): List<WeatherReport> {

        var data: MutableList<WeatherReport> = emptyList<WeatherReport>().toMutableList()

        for (element in type.daily){
            data.add(
                WeatherReport(
                    date = element.date,
                    minTemp = element.temp.min,
                    maxTemp = element.temp.max,
                    main = element.weather[0].main,
                    description = element.weather[0].description,
                    humidity = element.humidity,
                    icon = element.weather[0].icon
                )
            )
        }
        return data
        println(data)
    }



}