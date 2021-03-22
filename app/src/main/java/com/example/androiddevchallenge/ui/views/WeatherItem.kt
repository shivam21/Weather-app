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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.enums.WeatherType
import com.example.androiddevchallenge.models.CityItem

@Composable
fun WeatherItem(currentCityItem: CityItem?) {
    return when (currentCityItem?.weather) {
        WeatherType.SNOW.type -> {
            Image(
                ImageBitmap.imageResource(R.drawable.bg_snow),
                "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            WeatherParticles(WeatherType.SNOW)
        }
        WeatherType.HAZE.type -> {
            Image(
                ImageBitmap.imageResource(R.drawable.bg_haze),
                "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            WeatherParticles(WeatherType.HAZE)
        }
        WeatherType.THUNDER.type -> {
            Image(
                ImageBitmap.imageResource(R.drawable.bg_thunder),
                "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            WeatherParticles(WeatherType.THUNDER)
        }
        WeatherType.SUNNY.type -> {
            Image(
                ImageBitmap.imageResource(R.drawable.bg_clear_day),
                "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            WeatherParticles(WeatherType.SUNNY)
        }
        WeatherType.RAIN.type -> {
            Image(
                ImageBitmap.imageResource(R.drawable.bg_rain),
                "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            WeatherParticles(WeatherType.RAIN)
        }
        else -> {
        }
    }
}
