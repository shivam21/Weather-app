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

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class WeatherViewSensorEventListener(
    context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val onAngleChanged: (Float) -> Unit
) :
    SensorEventListener, LifecycleObserver {

    fun addObserver() {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val sensorManager by lazy { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    private var magneticValues: FloatArray? = null
    private var accelerometerValues: FloatArray? = null
    var started = false
        private set

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_MAGNETIC_FIELD -> magneticValues = event.values.copyOf()
            Sensor.TYPE_ACCELEROMETER -> accelerometerValues = event.values.copyOf()
        }

        if (magneticValues != null && accelerometerValues != null) {

            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrix(
                rotationMatrix,
                null,
                accelerometerValues,
                magneticValues
            )

            val remappedRotationMatrix = FloatArray(9)
            SensorManager.remapCoordinateSystem(
                rotationMatrix,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                remappedRotationMatrix
            )

            val orientationAngles = FloatArray(3)
            SensorManager.getOrientation(remappedRotationMatrix, orientationAngles)

            val pitch = Math.toDegrees(orientationAngles[1].toDouble())
            val roll = Math.toDegrees(orientationAngles[2].toDouble())

            if ((-85.0..85.0).contains(pitch))
                onAngleChanged(360 - roll.toFloat())
        }
    }

    private fun registerListener() {
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_UI
        )
    }

    private fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun start() {
        started = true
        registerListener()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop() {
        started = false
        unregisterListener()
    }
}
