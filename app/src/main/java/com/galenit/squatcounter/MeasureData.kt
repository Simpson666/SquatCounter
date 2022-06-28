package com.galenit.squatcounter

class MeasureData {
    // points from accelerometr
    private val accData: MutableList<Point> = mutableListOf()
    private val data: MutableList<MeasurePoint> = mutableListOf()

    fun addPoint(p: Point) {
        accData.add(p)
    }

    fun process() {
        for (i in 0 until accData.size) {
            val p: Point = accData[i]
            var speed = 0f
            if (i > 0) {
                speed = data[i - 1].speedAfter
            }
            data.add(MeasurePoint(p.getX(), p.getY(), p.getZ(), speed, p.getTime(), averagePoint))
        }
    }

//    @Throws(Throwable::class)
//    fun saveExt(con: Context, fname: String?): Boolean {
//        try {
//            val file = File(con.getExternalFilesDir(null), fname)
//            val os = FileOutputStream(file)
//            val out = OutputStreamWriter(os)
//            for (i in 0 until data.size()) {
//                val m: MeasurePoint = data.get(i)
//                out.write(m.getStoreString())
//            }
//            out.close()
//        } catch (t: Throwable) {
//            throw t
//        }
//        return true
//    }

    private val averagePoint: Point
        get() {
            var x = 0f
            var y = 0f
            var z = 0f
            var time = 0L
            for (i in 0 until accData.size) {
                val p: Point = accData[i]
                x += p.getX()
                y += p.getY()
                z += p.getZ()
                time += p.getTime()
            }
            return Point(x, y, z, 1, time)
        }
    val lastSpeed: Float
        get() = data.lastOrNull()?.speedAfter ?: 0.0f
    val lastSpeedKm: Float
        get() {
            val ms = lastSpeed
            return ms * 3.6f
        }
}