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
package com.example.androiddevchallenge.models

import android.graphics.BlurMaskFilter
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import com.example.androiddevchallenge.enums.WeatherType

const val SNOW_RADIUS = 10f
const val HAZE_RADIUS = 7.5f
const val RAIN_STRETCH = 15f

data class Particle(
    private val startXPos: Float,
    private val startYPos: Float,
    private val xPosRange: Float,
    private val yPosRange: Float,
    private val minSpeed: Float,
    private val speedRange: Float,
    private val image: List<ImageBitmap>
) {
    private var speed = minSpeed + Math.random().toFloat() * speedRange
    var xPos: Float = startXPos + Math.random().toFloat() * xPosRange
        private set

    var yPos: Float = startYPos + Math.random().toFloat() * yPosRange
        private set

    fun updatePhysics(change: Float, weatherType: WeatherType) {
        when (weatherType) {
            WeatherType.HAZE -> {
                val ran = Math.random()
                when {
                    ran <= 0.2 -> {
                        xPos += change * speed
                        yPos += change * speed
                    }
                    ran <= 0.4 -> {
                        xPos += change * speed
                        yPos -= change * speed
                    }
                    ran <= 0.6 -> {
                        xPos -= change * speed
                        yPos -= change * speed
                    }
                    else -> {
                        xPos -= change * speed
                        yPos += change * speed
                    }
                }
            }
            WeatherType.SUNNY -> {
                yPos += 0.01f * change * speed
            }
            else -> {
                yPos += 0.5f * change * speed
            }
        }
    }

    private var currentAlpha = 1f

    fun onDraw(canvas: DrawScope, weatherType: WeatherType) {
        when (weatherType) {
            WeatherType.RAIN -> {
                val scaleFactor = 2f
                val rainStretch = RAIN_STRETCH * (scaleFactor + 1.0f) / 2f
                val x1 = xPos
                val y1 = yPos - 0.01f * speed * (rainStretch)
                val x2 = xPos
                val y2 = yPos + 0.01f * speed * (rainStretch)
                val brush = Brush.linearGradient(
                    listOf(
                        Color.Transparent,
                        Color.White,
                        Color.White,
                        Color.Transparent
                    ),
                    start = Offset(x1, y1),
                    end = Offset(x2, y2)
                )
                canvas.drawLine(
                    brush, start = Offset(x1, y1),
                    end = Offset(x2, y2), strokeWidth = 1f
                )
            }
            WeatherType.SNOW -> {
                val scaleFactor = 1
                val brush = Brush.radialGradient(
                    colors = listOf(Color.White, Color.White, Color.Transparent, Color.Transparent),
                    center = Offset(xPos, yPos),
                    radius = (SNOW_RADIUS * scaleFactor),
                )
                canvas.drawCircle(
                    brush = brush,
                    radius = (SNOW_RADIUS * scaleFactor),
                    center = Offset(xPos, yPos),
                    alpha = currentAlpha
                )
            }
            WeatherType.HAZE -> {
                val scaleFactor = 1
                val brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(0.8f),
                        Color.Transparent
                    ),
                    center = Offset(0.1f * speed * xPos, 0.1f * speed * yPos),
                    radius = (HAZE_RADIUS * scaleFactor),
                )
                canvas.drawCircle(
                    brush = brush,
                    radius = (HAZE_RADIUS * scaleFactor),
                    center = Offset(0.1f * speed * xPos, 0.1f * speed * yPos)
                )
                canvas.drawIntoCanvas {
                    it.nativeCanvas.drawCircle(
                        0.1f * speed * xPos,
                        0.1f * speed * yPos,
                        (SNOW_RADIUS * scaleFactor),
                        Paint().asFrameworkPaint().apply {
                            isAntiAlias = true
                            isDither = true
                            style = android.graphics.Paint.Style.STROKE
                            color = android.graphics.Color.YELLOW
                            strokeWidth = 2f
                            maskFilter = BlurMaskFilter(2 * HAZE_RADIUS, BlurMaskFilter.Blur.NORMAL)
                        }
                    )
                }
            }
            WeatherType.THUNDER -> {
                val list = createLightning(canvas)
                val path = Path()
                path.moveTo(canvas.size.width / 2, 0f)
                list.forEach {
                    path.lineTo(it.x, it.y)
                }
                canvas.drawPath(
                    path,
                    brush = Brush.linearGradient(listOf(Color.White.copy(0.8f), Color.Transparent)),
                    style = Stroke(width = 6f)
                )
                canvas.drawIntoCanvas {
                    it.nativeCanvas.drawPath(
                        path.asAndroidPath(),
                        Paint().asFrameworkPaint().apply {
                            isAntiAlias = true
                            isDither = true
                            style = android.graphics.Paint.Style.STROKE
                            // color = android.graphics.Color.argb(235, 163, 245, 245)
                            shader = LinearGradientShader(
                                Offset(0f, 0f), Offset(0f, canvas.size.height * 0.8f),
                                listOf(
                                    Color(163, 245, 245),
                                    Color.Transparent
                                )
                            )
                            strokeWidth = 8f
                            maskFilter = BlurMaskFilter(15f, BlurMaskFilter.Blur.NORMAL)
                        }
                    )
                }
            }
            WeatherType.SUNNY -> {
                canvas.rotate(yPos, pivot = Offset(canvas.center.x, canvas.center.y / 2)) {
                    canvas.drawImage(image[0], Offset(canvas.center.x, canvas.center.y / 2))
                }
                canvas.translate(yPos, canvas.center.y / 4) {
                    canvas.drawImage(image[1], Offset(canvas.center.x / 2, canvas.center.y / 8))
                }
                canvas.translate(yPos, canvas.center.y / 6) {
                    canvas.drawImage(image[1], Offset(canvas.center.x / 2, canvas.center.y / 8))
                }
            }
        }
    }
}

data class LightItem(val x: Float, val y: Float)

fun createLightning(drawScope: DrawScope): MutableList<LightItem> {
    val roughness = 2
    val size = drawScope.size.width
    val center = LightItem(size / 2, 20f)
    val groundHeight = 0.7 * drawScope.size.height
    val maxDifference = size / 5f // "size" is the width and height of the canvas.
    val minSegmentHeight = 5
    // The main segment's height
    val y = groundHeight + (Math.random() - 0.9) * 50
    var segmentHeight = y - center.y
    var lightning = mutableListOf<LightItem>()
    // The start and the end position of the lightning.
    lightning.add(LightItem(center.x, center.y))
    lightning.add(
        LightItem(
            (Math.random() * (size - 100) + 50).toFloat(),
            y.toFloat()
        )
    )
    // This is important so we don't change the global one.
    var currDiff = maxDifference
    while (segmentHeight > minSegmentHeight) {
        // This uses the double buffering pattern
        val newSegments = mutableListOf<LightItem>()
        for (i in 0 until lightning.size - 1) {
            // The start and the end position of the current segment
            val start = lightning[i]
            val end = lightning[i + 1]
            // "midX" is the average X position of the segment
            val midX = (start.x + end.x) / 2
            val newX = midX + (Math.random() * 2 - 1).toFloat() * currDiff
            // Add the start and the middle point to the new segment list
            // Because the end position is going to be added again in the next iteration
            // we don't need to add that here.
            newSegments.add(start)
            newSegments.add(LightItem(newX, (start.y + end.y) / 2))
        }
        // Add the last point of the lightning to the segments.
        newSegments.add(lightning.removeLast())
        lightning = newSegments

        currDiff /= roughness.toFloat()
        segmentHeight /= 2
    }
    return lightning
}
