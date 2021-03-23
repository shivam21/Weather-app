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

import android.content.res.Resources
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.enums.WeatherType
import com.example.androiddevchallenge.utils.ParticleSystem
import com.example.androiddevchallenge.utils.WeatherViewSensorEventListener

@Composable
fun WeatherParticles(weatherType: WeatherType) {
    var angleState by mutableStateOf(0f)
    if (weatherType != WeatherType.SUNNY) {
        WeatherViewSensorEventListener(LocalContext.current, LocalLifecycleOwner.current) {
            angleState = it
        }.apply {
            addObserver()
        }
    }
    val displayMetrics = Resources.getSystem().displayMetrics
    val screenHeight = displayMetrics.heightPixels
    val screenWidth = displayMetrics.widthPixels
    val particleSystem = ParticleSystem(
        startXPos = 0f,
        startYPos = 0f,
        endXPos = screenWidth.toFloat(),
        endYPos = screenHeight.toFloat(),
        xPosRange = screenWidth.toFloat(),
        yPosRange = screenWidth * 0.1f,
        minSpeed = when (weatherType) {
            WeatherType.HAZE -> 10f
            WeatherType.SNOW -> 10f
            else -> 100f
        },
        speedRange = when (weatherType) {
            WeatherType.HAZE -> 10f
            WeatherType.SNOW -> 10f
            else -> 100f
        },
        image = listOf(
            ImageBitmap.imageResource(id = R.drawable.sun),
            ImageBitmap.imageResource(id = R.drawable.cloudy)
        ),
        numParticles = when (weatherType) {
            WeatherType.THUNDER -> 2
            WeatherType.SUNNY -> 1
            else -> 100
        }
    )
    val transition = rememberInfiniteTransition()
    val state by transition.animateFloat(
        0f, 1f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 300,
                easing = FastOutLinearInEasing
            )
        )
    )
    Canvas(
        modifier = Modifier.fillMaxSize().background(Color.Transparent).semantics(true) {
            contentDescription = "weather type:${weatherType.type}"
        },
        onDraw = {
            rotate(angleState) {
                particleSystem.doDraw(this, weatherType)
                particleSystem.updatePhysics(1f - state, weatherType)
            }
        }
    )
}
