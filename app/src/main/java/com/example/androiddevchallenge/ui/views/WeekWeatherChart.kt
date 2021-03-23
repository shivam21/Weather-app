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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.models.WeekWeatherItem
import com.example.androiddevchallenge.utils.TwoLinesChart

@Composable
fun WeekWeatherChart(weekWeatherList: List<WeekWeatherItem>) {
    Column(Modifier.padding(top = 50.dp, bottom = 100.dp)) {
//        LineChart(
//            LineChartData(
//                weekWeatherList.map {
//                    LineChartData.Point(it.maxTemp.toFloat(), true)
//                }
//            ),
//            modifier = Modifier.height(50.dp)
//        )
//        LineChart(
//            LineChartData(
//                weekWeatherList.map {
//                    LineChartData.Point(it.minTemp.toFloat(), false)
//                }
//            ),
//            modifier = Modifier.height(50.dp)
//        )
        TwoLinesChart(
            maxPaths = weekWeatherList.map { it.maxTemp },
            minPaths = weekWeatherList.map { it.minTemp },
            modifier = Modifier.height(200.dp)
        )
    }
}
