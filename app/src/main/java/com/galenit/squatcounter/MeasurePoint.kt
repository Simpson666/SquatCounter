package com.galenit.squatcounter

import kotlin.math.sqrt

class MeasurePoint(
    private val x: Float,
    private val y: Float,
    private val z: Float,
    private val speedBefore: Float,
    private val time: Long,
    private val averagePoint: Point
) {
    var speedAfter = 0f
        private set
    private var distance = 0f
        private set
    private var acceleration = 0f
        private set

    init {
        calc()
    }

    private fun calc() {
        //Acceleration as projection of current vector on average
        acceleration = x * averagePoint.getX() + y * averagePoint.getY() + z * averagePoint.getZ()
        acceleration /= sqrt(averagePoint.force.toDouble()).toFloat()
        val t = time.toFloat() / 1000f
        speedAfter = speedBefore + acceleration * t
        distance = speedBefore * t + acceleration * t * t / 2
    }
}