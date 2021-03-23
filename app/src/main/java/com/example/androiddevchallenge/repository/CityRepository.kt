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
            "Updated 2 min ago",
            listOf(
                WeekWeatherItem(WeatherType.RAIN, 4, 10),
                WeekWeatherItem(WeatherType.SUNNY, 3, 12),
                WeekWeatherItem(WeatherType.SNOW, 2, 11),
                WeekWeatherItem(WeatherType.HAZE, 4, 14),
                WeekWeatherItem(WeatherType.RAIN, 5, 13),
                WeekWeatherItem(WeatherType.SUNNY, 3, 12)
            )
        ),
        CityItem(
            WeatherType.SUNNY.type, 35, 10, "America",
            "Updated 3 min ago",
            listOf(
                WeekWeatherItem(WeatherType.SUNNY, 2, 12),
                WeekWeatherItem(WeatherType.RAIN, 4, 14),
                WeekWeatherItem(WeatherType.HAZE, 3, 11),
                WeekWeatherItem(WeatherType.SNOW, 5, 10),
                WeekWeatherItem(WeatherType.RAIN, 6, 13),
                WeekWeatherItem(WeatherType.SUNNY, 3, 14)
            )
        ),
        CityItem(
            WeatherType.SNOW.type, -4, 254, "Greenland",
            "Just Updated",
            listOf(
                WeekWeatherItem(WeatherType.SNOW, 11, 32),
                WeekWeatherItem(WeatherType.RAIN, 12, 30),
                WeekWeatherItem(WeatherType.SNOW, 17, 29),
                WeekWeatherItem(WeatherType.HAZE, 9, 31),
                WeekWeatherItem(WeatherType.THUNDER, 16, 34),
                WeekWeatherItem(WeatherType.SUNNY, 13, 33)
            )
        ),
        CityItem(
            WeatherType.THUNDER.type, 8, 287, "Africa",
            "Updated 1 min ago",
            listOf(
                WeekWeatherItem(WeatherType.THUNDER, 6, 12),
                WeekWeatherItem(WeatherType.SUNNY, 7, 15),
                WeekWeatherItem(WeatherType.SNOW, 2, 7),
                WeekWeatherItem(WeatherType.HAZE, 5, 10),
                WeekWeatherItem(WeatherType.THUNDER, 7, 15),
                WeekWeatherItem(WeatherType.SUNNY, 9, 14)
            )
        ),
        CityItem(
            WeatherType.HAZE.type, 1, 325, "New Zealand",
            "Updated 3 min ago",
            listOf(
                WeekWeatherItem(WeatherType.HAZE, 12, 29),
                WeekWeatherItem(WeatherType.SUNNY, 14, 26),
                WeekWeatherItem(WeatherType.SNOW, 9, 28),
                WeekWeatherItem(WeatherType.HAZE, 10, 31),
                WeekWeatherItem(WeatherType.RAIN, 13, 27),
                WeekWeatherItem(WeatherType.THUNDER, 11, 32)
            )
        )
    )
}
