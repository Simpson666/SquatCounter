package com.galenit.squatcounter

import android.hardware.Sensor
import android.hardware.SensorEventListener

abstract class Accelerometer: SensorEventListener {
    var lastX: Float = 0.0f
    var lastY: Float = 0.0f
    var lastZ: Float = 0.0f
    var lastTime: Long = 0
    abstract fun getPoint(): Point?
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
}