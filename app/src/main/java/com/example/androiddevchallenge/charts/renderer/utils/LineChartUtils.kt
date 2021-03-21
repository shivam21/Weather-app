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
package com.example.androiddevchallenge.charts.renderer.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

object LineChartUtils {
    fun calculateDrawableArea(
        xAxisDrawableArea: Rect,
        yAxisDrawableArea: Rect,
        size: Size,
        offset: Float
    ): Rect {
        val horizontalOffset = xAxisDrawableArea.width * offset / 100f

        return Rect(
            left = yAxisDrawableArea.right,
            top = 0f,
            bottom = xAxisDrawableArea.top,
            right = size.width - horizontalOffset
        )
    }

    fun calculateXAxisDrawableArea(
        yAxisWidth: Float,
        labelHeight: Float,
        size: Size
    ): Rect {
        val top = size.height - labelHeight

        return Rect(
            left = yAxisWidth,
            top = top,
            bottom = size.height,
            right = size.width
        )
    }

    fun Density.calculateYAxisDrawableArea(
        xAxisLabelSize: Float,
        size: Size
    ): Rect {
        // Either 50dp or 10% of the chart width.
        val right = minOf(50.dp.toPx(), size.width * 10f / 100f)

        return Rect(
            left = 0f,
            top = 0f,
            bottom = size.height - xAxisLabelSize,
            right = right
        )
    }

    fun calculatePointLocation(
        drawableArea: Rect,
        lineChartData: LineChartData,
        point: LineChartData.Point,
        index: Int
    ): Offset {
        val x = (index.toFloat() / (lineChartData.points.size - 1))
        val y = ((point.value - lineChartData.minYValue) / lineChartData.yRange)

        return Offset(
            x = (x * drawableArea.width) + drawableArea.left,
            y = drawableArea.height - (y * drawableArea.height)
        )
    }

    fun withProgress(
        index: Int,
        lineChartData: LineChartData,
        transitionProgress: Float,
        showWithProgress: (progress: Float) -> Unit
    ) {
        val size = lineChartData.points.size
        val toIndex = (size * transitionProgress).toInt() + 1

        if (index == toIndex) {
            // Get the left over.
            val sizeF = lineChartData.points.size.toFloat()
            val perIndex = (1f / sizeF)
            val down = (index - 1) * perIndex

            showWithProgress((transitionProgress - down) / perIndex)
        } else if (index < toIndex) {
            showWithProgress(1f)
        }
    }

    fun calculateLinePath(
        drawableArea: Rect,
        lineChartData: LineChartData,
        transitionProgress: Float
    ): Path = Path().apply {
        var prevPointLocation: Offset? = null
        lineChartData.points.forEachIndexed { index, point ->
            withProgress(
                index = index,
                transitionProgress = transitionProgress,
                lineChartData = lineChartData
            ) { progress ->
                val pointLocation = calculatePointLocation(
                    drawableArea = drawableArea,
                    lineChartData = lineChartData,
                    point = point,
                    index = index
                )

                if (index == 0) {
                    moveTo(0f, 10f)
                    lineTo(pointLocation.x, pointLocation.y)
                } else {
                    if (progress <= 1f) {
                        // We have to change the `dy` based on the progress
                        val prevX = prevPointLocation!!.x
                        val prevY = prevPointLocation!!.y

                        val x = (pointLocation.x - prevX) * progress + prevX
                        val y = (pointLocation.y - prevY) * progress + prevY

                        lineTo(x, y)
                    } else {
                        lineTo(pointLocation.x, pointLocation.y)
                    }
                }

                prevPointLocation = pointLocation
            }
        }
        lineTo(drawableArea.right + 100f, 10f)
    }
}
