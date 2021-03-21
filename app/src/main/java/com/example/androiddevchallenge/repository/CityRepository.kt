/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.enums.WeatherType
import com.example.androiddevchallenge.models.CityItem
import com.example.androiddevchallenge.models.WeekWeatherItem

object CityRepository {

    fun getCityList() = listOf(
        CityItem(
            WeatherType.RAIN.type,
            22,
            100,
            "Alaska",
            listOf(
                WeekWeatherItem(WeatherType.RAIN, 17, 32),
                WeekWeatherItem(WeatherType.SUNNY, 19, 34),
                WeekWeatherItem(WeatherType.SNOW, 13, 43),
                WeekWeatherItem(WeatherType.HAZE, 18, 30),
                WeekWeatherItem(WeatherType.RAIN, 20, 35),
                WeekWeatherItem(WeatherType.SUNNY, 14, 27)
            )
        ),
        CityItem(
            WeatherType.SUNNY.type, 35, 10, "America",
            listOf(
                WeekWeatherItem(WeatherType.SUNNY),
                WeekWeatherItem(WeatherType.RAIN),
                WeekWeatherItem(WeatherType.HAZE),
                WeekWeatherItem(WeatherType.SNOW),
                WeekWeatherItem(WeatherType.RAIN),
                WeekWeatherItem(WeatherType.SUNNY)
            )
        ),
        CityItem(
            WeatherType.SNOW.type, -4, 254, "Greenland",
            listOf(
                WeekWeatherItem(WeatherType.SNOW),
                WeekWeatherItem(WeatherType.RAIN),
                WeekWeatherItem(WeatherType.SNOW),
                WeekWeatherItem(WeatherType.HAZE),
                WeekWeatherItem(WeatherType.THUNDER),
                WeekWeatherItem(WeatherType.SUNNY)
            )
        ),
        CityItem(
            WeatherType.THUNDER.type, 8, 287, "Africa",
            listOf(
                WeekWeatherItem(WeatherType.THUNDER),
                WeekWeatherItem(WeatherType.SUNNY),
                WeekWeatherItem(WeatherType.SNOW),
                WeekWeatherItem(WeatherType.HAZE),
                WeekWeatherItem(WeatherType.THUNDER),
                WeekWeatherItem(WeatherType.SUNNY)
            )
        ),
        CityItem(
            WeatherType.HAZE.type, 1, 325, "New Zealand",
            listOf(
                WeekWeatherItem(WeatherType.HAZE),
                WeekWeatherItem(WeatherType.SUNNY),
                WeekWeatherItem(WeatherType.SNOW),
                WeekWeatherItem(WeatherType.HAZE),
                WeekWeatherItem(WeatherType.RAIN),
                WeekWeatherItem(WeatherType.THUNDER)
            )
        )
    )
}
