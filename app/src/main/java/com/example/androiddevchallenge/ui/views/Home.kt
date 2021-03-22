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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
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
                modifier = Modifier
                    .padding(top = 10.dp),
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    "${cityItemState?.temperature}°",
                    style = MaterialTheme.typography.h3,
                    color = Color.White,
                    modifier = Modifier.padding(start = 18.dp)
                )
                Text(
                    "${cityItemState?.weather} ${cityItemState?.windSpeed}m/s",
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White,
                    modifier = Modifier.padding(start = 18.dp)
                )
                WeekWeather(cityItemState?.weekWeatherList.orEmpty())
                WeekWeatherChart(cityItemState?.weekWeatherList.orEmpty())
            }
        }
    }
}
