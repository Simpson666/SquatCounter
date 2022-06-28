package com.galenit.squatcounter

import android.hardware.SensorEvent

class XYZSensors : Accelerometer() {
    // calibration
    private var dX = 0f
    private var dY = 0f
    private var dZ = 0f

    // buffer variables
    private var X = 0f
    private var Y = 0f
    private var Z = 0f
    private var count = 0
        private set
    private var lastTimestamp: Long = 0
    private var time: Long = 0

    var onSensorDataChanged: () -> Unit = {  }

    // returns last SenorEvent parameters
    val lastPoint: Point
        get() = Point(lastX, lastY, lastZ, 1, lastTime)

    // returrns parameters, using buffer: average acceleration
    // since last call of getPoint().
    override fun getPoint(): Point {
        if (count == 0) {
            return Point(lastX, lastY, lastZ, 1, lastTime)
        }
        val p = Point(X, Y, Z, count, time)
        reset()
        return p
    }

    // resets buffer
    fun reset() {
        count = 0
        X = 0f
        Y = 0f
        Z = 0f
        lastTime = 0L
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0] + dX
        val y = event.values[1] + dY
        val z = event.values[2] + dZ
        lastX = x
        lastY = y
        lastZ = z

        if (lastTimestamp == 0L)
            lastTime = 0
        else
            lastTime = event.timestamp - lastTimestamp
        lastTimestamp = event.timestamp

        X += x
        Y += y
        Z += z
        time += lastTime
        if (count < BUFFER_SIZE - 1) {
            count++
        } else {
            reset()
        }
        onSensorDataChanged()
    }

    fun setdX(dX: Float) {
        this.dX = dX
    }

    fun setdY(dY: Float) {
        this.dY = dY
    }

    fun setdZ(dZ: Float) {
        this.dZ = dZ
    }

    companion object {
        private const val BUFFER_SIZE = 500
    }
}