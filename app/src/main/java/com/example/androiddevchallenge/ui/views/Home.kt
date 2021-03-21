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
package com.example.androiddevchallenge.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.charts.LineChart
import com.example.androiddevchallenge.charts.renderer.utils.LineChartData
import com.example.androiddevchallenge.enums.WeatherType
import com.example.androiddevchallenge.models.CityItem
import com.example.androiddevchallenge.models.WeekWeatherItem
import com.example.androiddevchallenge.navigation.Screens
import com.example.androiddevchallenge.viewmodels.HomeViewModel

@Composable
fun Home(
    navController: NavHostController = NavHostController(LocalContext.current),
    viewModel: HomeViewModel
) {
    val navigateToSelectCity = { navController.navigate(Screens.SelectCity.route) }
    HomeItem(navigateToSelectCity, viewModel)
}

@Composable
fun HomeItem(navigateToSelectCity: () -> Unit, viewModel: HomeViewModel) {
    val cityItemState by viewModel.currentCityItem.observeAsState()
    Scaffold {
        Box(Modifier.fillMaxSize()) {
            WeatherItem(cityItemState)
            HomeTopAppBar(cityItemState, navigateToSelectCity)
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(top = 10.dp),
            ) {
                Text(
                    "${cityItemState?.temperature}°",
                    style = MaterialTheme.typography.h5,
                    color = Color.White,
                    modifier = Modifier.padding(start = 18.dp)
                )
                Text(
                    "${cityItemState?.weather} ${cityItemState?.windSpeed}m/s",
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    modifier = Modifier.padding(start = 18.dp)
                )
                WeekWeather(cityItemState?.weekWeatherList.orEmpty())
                WeekWeatherChart(cityItemState?.weekWeatherList.orEmpty())
            }
        }
    }
}

@Composable
fun WeekWeatherChart(weekWeatherList: List<WeekWeatherItem>) {
    Column(Modifier.padding(top = 50.dp)) {
        LineChart(
            LineChartData(
                weekWeatherList.map {
                    LineChartData.Point(it.maxTemp.toFloat(), true)
                }
            ),
            modifier = Modifier.height(50.dp)
        )
        LineChart(
            LineChartData(
                weekWeatherList.map {
                    LineChartData.Point(it.minTemp.toFloat(), false)
                }
            ),
            modifier = Modifier.height(50.dp)
        )
    }
}

@Composable
fun WeekWeather(weekWeatherList: List<WeekWeatherItem>) {
    val viewModel: HomeViewModel = viewModel()
    Row(Modifier.padding(top = 20.dp)) {
        weekWeatherList.forEachIndexed { index, weather ->
            Column(modifier = Modifier.weight(1f).padding(start = 4.dp, end = 4.dp)) {
                Text(
                    viewModel.getDay(index),
                    color = Color.White,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    ),
                    fontSize = 12.sp
                )
                Text(
                    viewModel.getDate(index),
                    color = Color.White,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    ),
                    fontSize = 12.sp
                )
                Icon(
                    painterResource(id = getDrawableId(weather.weather)),
                    "",
                    modifier = Modifier.size(24.dp).align(Alignment.CenterHorizontally)
                        .padding(top = 4.dp, bottom = 2.dp),
                    tint = Color.White
                )
                Text(
                    weather.weather.type,
                    color = Color.White,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    ),
                    fontSize = 12.sp
                )
            }
        }
    }
}

fun getDrawableId(weather: WeatherType): Int {
    return when (weather) {
        WeatherType.THUNDER -> R.drawable.ic_lighting
        WeatherType.RAIN -> R.drawable.ic_rain
        WeatherType.SUNNY -> R.drawable.ic_baseline_wb_sunny_24
        WeatherType.HAZE -> R.drawable.ic_fog
        else -> R.drawable.ic_snowflake
    }
}

@Composable
fun HomeTopAppBar(cityItemState: CityItem?, navigateToSelectCity: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(12.dp).padding(top = 30.dp)
    ) {
        Row(Modifier) {
            Icon(
                painterResource(R.drawable.ic_baseline_location_on_24),
                "",
                tint = Color.White
            )
            Text(
                cityItemState?.cityName.orEmpty(),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 10.dp),
                color = Color.White
            )
        }

        Icon(
            painterResource(R.drawable.ic_baseline_location_city_24), "",
            Modifier.align(Alignment.CenterEnd)
                .padding(end = 20.dp)
                .clickable { navigateToSelectCity() },
            tint = Color.White
        )
    }
}

@Composable
fun WeatherItem(currentCityItem: CityItem?) {
    return when (currentCityItem?.weather) {
        WeatherType.SNOW.type -> {
            WeatherParticles(WeatherType.SNOW)
        }
        WeatherType.HAZE.type -> {
            WeatherParticles(WeatherType.HAZE)
        }
        WeatherType.THUNDER.type -> {
            Box {
                WeatherParticles(WeatherType.THUNDER)
            }
        }
        WeatherType.SUNNY.type -> {
            WeatherParticles(WeatherType.SUNNY)
        }
        else -> {
            WeatherParticles(WeatherType.RAIN)
        }
    }
}
