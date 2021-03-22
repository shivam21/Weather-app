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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.models.WeekWeatherItem
import com.example.androiddevchallenge.utils.getDrawableId
import com.example.androiddevchallenge.viewmodels.HomeViewModel

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
