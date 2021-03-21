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
package com.example.androiddevchallenge.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.androiddevchallenge.enums.WeatherType
import com.example.androiddevchallenge.models.Particle

class ParticleSystem(
    private val endYPos: Float,
    private val endXPos: Float,
    private val startYPos: Float,
    private val startXPos: Float,
    private val xPosRange: Float,
    private val yPosRange: Float,
    private val minSpeed: Float,
    private val speedRange: Float,
    private val image: ImageBitmap,
    numParticles: Int
) {
    private val particles = mutableListOf<Particle>()

    init {
        for (i in 0 until numParticles) {
            val particle = Particle(
                startXPos = startXPos,
                startYPos = startYPos,
                xPosRange = xPosRange,
                yPosRange = yPosRange,
                minSpeed = minSpeed,
                speedRange = speedRange,
                image = image
            )
            particles.add(particle)
        }
    }

    fun doDraw(canvas: DrawScope, weatherType: WeatherType) {
        for (particle in particles) {
            particle.onDraw(canvas, weatherType)
        }
    }

    fun updatePhysics(altDelta: Float, weatherType: WeatherType) {
        particles.forEachIndexed { index, particle ->
            particle.updatePhysics(altDelta, weatherType = weatherType)

            if (particle.xPos !in startXPos..endXPos || particle.yPos !in startYPos..endYPos) {
                particles[index] = Particle(
                    startXPos = startXPos,
                    startYPos = startYPos,
                    xPosRange = xPosRange,
                    yPosRange = yPosRange,
                    minSpeed = minSpeed,
                    speedRange = speedRange,
                    image = image
                )
            }
        }
    }
}
