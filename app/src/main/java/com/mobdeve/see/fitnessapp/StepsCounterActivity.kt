package com.mobdeve.see.fitnessapp

import android.content.Context
import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobdeve.see.fitnessapp.databinding.*

class StepsCounterActivity : AppCompatActivity(), SensorEventListener{
    private lateinit var sensorManager: SensorManager
    private var running: Boolean = false
    private lateinit var tvSteps: TextView
    private lateinit var fab: FloatingActionButton
    private var steps: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityStepCounterBinding = ActivityStepCounterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        tvSteps = findViewById(R.id.tvSteps)
        fab = findViewById(R.id.btn_fab)
        tvSteps.text = steps.toString()

        loadData()
        resetSteps()

        fab.setOnClickListener {
            if (running) {
                running = false
                fab.setImageResource(R.drawable.baseline_play_arrow_24)
                Toast.makeText(this, "Counter Paused", Toast.LENGTH_SHORT).show()
            } else {
                activateSensor()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(running)
            activateSensor()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("steps", steps)
        outState.putBoolean("running", running)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        steps = savedInstanceState.getInt("steps", 0)
        running = savedInstanceState.getBoolean("running", false)
        tvSteps.text = steps.toString()
        if (running) {
            fab.setImageResource(R.drawable.baseline_pause_24)
        } else {
            fab.setImageResource(R.drawable.baseline_play_arrow_24)
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("StepsCounterActivity", "one step taken")
        if(running){
            steps++
            tvSteps.text = steps.toString()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    private fun activateSensor(){
        running = true
        fab.setImageResource(R.drawable.baseline_pause_24)
        Toast.makeText(this, "Counter Started", Toast.LENGTH_SHORT).show()

        val PHYSICAL_ACTIVITY = 100
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            if(sensor != null){
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
            }else{
                Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show()
            }
        }else{
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), PHYSICAL_ACTIVITY)
        }
    }

    private fun resetSteps(){
        tvSteps.setOnClickListener{
            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }
        tvSteps.setOnLongClickListener {
            steps = 0
            tvSteps.text = steps.toString()
            saveData()
            true
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("stepsTaken", steps)
        editor.apply()
    }
    private fun loadData(){
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedSteps = sharedPreferences.getInt("stepsTaken", 0)
        steps = savedSteps
    }
}



























