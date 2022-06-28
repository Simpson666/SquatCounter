package com.galenit.squatcounter

class Point(x: Float, y: Float, z: Float, count: Int, time: Long) {
    private var x = 0f
    private var y = 0f
    private var z = 0f
    private var count = 1
    private var time = 0L

    init {
        this.x = x
        this.y = y
        this.z = z
        this.count = count
        this.time = time
    }

    fun getX(): Float {
        return x / count.toFloat()
    }

    fun getY(): Float {
        return y / count.toFloat()
    }

    fun getZ(): Float {
        return z / count.toFloat()
    }

    fun getTime(): Long {
        return time / count
    }

    val force: Float
        get() = getX() * getX() + getY() * getY() + getZ() * getZ()
}