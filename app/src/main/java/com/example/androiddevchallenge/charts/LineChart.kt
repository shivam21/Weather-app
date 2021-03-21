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
package com.example.androiddevchallenge.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.example.androiddevchallenge.charts.renderer.line.LineDrawer
import com.example.androiddevchallenge.charts.renderer.line.SolidLineDrawer
import com.example.androiddevchallenge.charts.renderer.point.FilledCircularPointDrawer
import com.example.androiddevchallenge.charts.renderer.point.PointDrawer
import com.example.androiddevchallenge.charts.renderer.utils.LineChartData
import com.example.androiddevchallenge.charts.renderer.utils.LineChartUtils.calculateDrawableArea
import com.example.androiddevchallenge.charts.renderer.utils.LineChartUtils.calculateLinePath
import com.example.androiddevchallenge.charts.renderer.utils.LineChartUtils.calculatePointLocation
import com.example.androiddevchallenge.charts.renderer.utils.LineChartUtils.calculateXAxisDrawableArea
import com.example.androiddevchallenge.charts.renderer.utils.LineChartUtils.calculateYAxisDrawableArea
import com.example.androiddevchallenge.charts.renderer.utils.LineChartUtils.withProgress
import com.example.androiddevchallenge.charts.renderer.utils.simpleChartAnimation
import com.example.androiddevchallenge.charts.renderer.xaxis.SimpleXAxisDrawer
import com.example.androiddevchallenge.charts.renderer.xaxis.XAxisDrawer

@Composable
fun LineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: PointDrawer = FilledCircularPointDrawer(),
    lineDrawer: LineDrawer = SolidLineDrawer(),
    xAxisDrawer: XAxisDrawer = SimpleXAxisDrawer(),
    horizontalOffset: Float = 10f
) {
    val transitionAnimation = remember(lineChartData.points) { Animatable(initialValue = 0f) }

    LaunchedEffect(lineChartData.points) {
        transitionAnimation.snapTo(0f)
        transitionAnimation.animateTo(1f, animationSpec = animation)
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        drawIntoCanvas { canvas ->
            val yAxisDrawableArea = calculateYAxisDrawableArea(
                xAxisLabelSize = xAxisDrawer.requiredHeight(this),
                size = size
            )
            val xAxisDrawableArea = calculateXAxisDrawableArea(
                yAxisWidth = yAxisDrawableArea.width,
                labelHeight = xAxisDrawer.requiredHeight(this),
                size = size
            )

            val chartDrawableArea = calculateDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                yAxisDrawableArea = yAxisDrawableArea,
                size = size,
                offset = horizontalOffset
            )

            // Draw the chart line.
            lineDrawer.drawLine(
                drawScope = this,
                canvas = canvas,
                linePath = calculateLinePath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                )
            )

            lineChartData.points.forEachIndexed { index, point ->
                withProgress(
                    index = index,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                ) {
                    pointDrawer.drawPoint(
                        drawScope = this,
                        canvas = canvas,
                        center = calculatePointLocation(
                            drawableArea = chartDrawableArea,
                            lineChartData = lineChartData,
                            point = point,
                            index = index
                        ),
                        point = point
                    )
                }
            }
        }
    }
}
