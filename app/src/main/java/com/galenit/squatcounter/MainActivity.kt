package com.galenit.squatcounter

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.galenit.squatcounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager
    private var sensorGyroscope: Sensor? = null
    private var sensorAccelerometer: Sensor? = null
    private lateinit var xyzGyroscope: XYZSensors
    private lateinit var xyzAccelerometer: XYZSensors
    private lateinit var mdXYZAccelerometer: MeasureData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (sensorGyroscope == null) {
            toast("Отсутствует датчик гироскопа.")
        }
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (sensorGyroscope == null) {
            toast("Отсутствует датчик акселерометра.")
        }

        binding.buttonStart.setOnClickListener {
            when(binding.buttonStart.text) {
                getString(R.string.title_button_start) -> {
                    start()
                }
                getString(R.string.title_button_stop) -> {
                    stop()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun start() {
        binding.buttonStart.text = getString(R.string.title_button_stop)
        sensorGyroscope?.let {
            xyzGyroscope = XYZSensors()
            xyzGyroscope.onSensorDataChanged = {
                binding.gyroX.text = "X: %.4f".format(xyzGyroscope.lastX)
                binding.gyroY.text = "Y: %.4f".format(xyzGyroscope.lastY)
                binding.gyroZ.text = "Z: %.4f".format(xyzGyroscope.lastZ)
            }
            sensorManager.registerListener(xyzGyroscope, it, SensorManager.SENSOR_DELAY_UI)
        }
        sensorAccelerometer?.let {
            mdXYZAccelerometer = MeasureData()
            xyzAccelerometer = XYZSensors()
            xyzAccelerometer.onSensorDataChanged = {
                mdXYZAccelerometer.addPoint(xyzAccelerometer.getPoint())

                mdXYZAccelerometer.process()
                binding.speed.text = mdXYZAccelerometer.lastSpeedKm.toString()

                binding.accelerX.text = "X: %.4f".format(xyzAccelerometer.lastX)
                binding.accelerY.text = "Y: %.4f".format(xyzAccelerometer.lastY)
                binding.accelerZ.text = "Z: %.4f".format(xyzAccelerometer.lastZ)
            }
            sensorManager.registerListener(xyzAccelerometer, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    private fun stop() {
        binding.buttonStart.text = getString(R.string.title_button_start)

        sensorGyroscope?.let {
            sensorManager.unregisterListener(xyzGyroscope, it)

            binding.gyroX.text = "X: 0"
            binding.gyroY.text = "Y: 0"
            binding.gyroZ.text = "Z: 0"
        }
        sensorAccelerometer?.let {
            sensorManager.unregisterListener(xyzAccelerometer, it)

            binding.accelerX.text = "X: 0"
            binding.accelerY.text = "Y: 0"
            binding.accelerZ.text = "Z: 0"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}