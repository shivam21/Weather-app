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
package com.example.androiddevchallenge.charts.renderer.point

import android.text.TextPaint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.charts.renderer.utils.LineChartData
import com.example.androiddevchallenge.charts.renderer.utils.toLegacyInt

data class FilledCircularPointDrawer(
    val diameter: Dp = 8.dp,
    val color: Color = Color.White
) : PointDrawer {

    private val paint = Paint().apply {
        color = this@FilledCircularPointDrawer.color
        style = PaintingStyle.Fill
        isAntiAlias = true
    }

    private val textPaint = TextPaint().apply {
        color = this@FilledCircularPointDrawer.color.toLegacyInt()
        style = android.graphics.Paint.Style.FILL
        isAntiAlias = true
    }

    override fun drawPoint(
        drawScope: DrawScope,
        canvas: Canvas,
        center: Offset,
        point: LineChartData.Point
    ) {
        with(drawScope as Density) {
            canvas.drawCircle(center, diameter.toPx() / 2f, paint)
        }

        with(drawScope as Density) {
            textPaint.textSize = 8 * this.density
            val radius = diameter.toPx() / 2f
            drawScope.drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "${point.value.toInt()}°",
                    center.x - radius,
                    if (point.isTop) center.y + radius - 3 * radius else center.y + radius + 3 * radius,
                    textPaint
                )
            }
        }
    }
}
