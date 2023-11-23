package com.mobdeve.see.fitnessapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.mobdeve.see.fitnessapp.databinding.FragmentStepCounterBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



class StepCounterFragment : Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var running: Boolean = false
    private lateinit var tvSteps: TextView
    private lateinit var fab: FloatingActionButton
    private var steps: Int = 0
    private lateinit var userDao: UserDao
    private lateinit var stepLogDao: StepLogDao
    private lateinit var applicationContext: Context
    private lateinit var steplog : StepLog

    private lateinit var progress : CircularProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)

        applicationContext = context

        // Initialize context-dependent objects here
        val appDatabase = AppDatabase.getDatabase(context)
        userDao = appDatabase.userDao()
        stepLogDao = appDatabase.stepLogDao()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentStepCounterBinding = FragmentStepCounterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        tvSteps = view.findViewById(R.id.tvSteps)
        fab = view.findViewById(R.id.btn_fab)
        tvSteps.text = steps.toString()
        progress = view.findViewById(R.id.circularProgressBar)

        lifecycleScope.launch {
            val userId = arguments?.getInt("userId", 0) ?: 0
            val stepLogs = stepLogDao.getStepLogsForUser(userId)
        }




        val appDatabase = AppDatabase.getDatabase(requireContext())
        userDao = appDatabase.userDao()
        stepLogDao = appDatabase.stepLogDao()

        loadData()
        resetSteps()

        fab.setOnClickListener {
            if (running) {
                running = false
                fab.setImageResource(R.drawable.baseline_play_arrow_24)
                Toast.makeText(requireContext(), "Counter Paused", Toast.LENGTH_SHORT).show()
            } else {
                activateSensor()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(running)
            activateSensor()

        // For the progress bar
        val currentDate = getCurrentDate()
        lifecycleScope.launch {
            val userId = arguments?.getInt("userId", 0) ?: 0
            val stepLog = stepLogDao.getStepLogForUserAndDate(userId, currentDate)

            if (stepLog != null) {
                progress.progressMax = stepLog.goal.toFloat()
            }
        }
        
        progress.progress = steps.toFloat()


    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("steps", steps)
        outState.putBoolean("running", running)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
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
            updateDatabase(steps)

            progress.apply {
                setProgressWithAnimation(steps.toFloat())
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    private fun activateSensor(){
        running = true
        fab.setImageResource(R.drawable.baseline_pause_24)
        Toast.makeText(applicationContext, "Counter Started", Toast.LENGTH_SHORT).show()

        val PHYSICAL_ACTIVITY = 100
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            if(sensor != null){
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
            }else{
                Toast.makeText(applicationContext, "Sensor not found", Toast.LENGTH_SHORT).show()
            }
        }else{
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), PHYSICAL_ACTIVITY)
        }
    }

    private fun resetSteps(){
        tvSteps.setOnClickListener{
            Toast.makeText(applicationContext, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }
        tvSteps.setOnLongClickListener {
            steps = 0
            updateDatabase(steps)
            true
        }
    }

    private fun updateDatabase(steps: Int) {
        // Retrieve userId from fragment arguments
        val userId = arguments?.getInt("userId", 0) ?: 0
        val date = getTodayDate()
        lifecycleScope.launch {
            val stepLog = stepLogDao.getStepLogForUserAndDate(userId, date)
            if (stepLog != null) {
                // If a log exists for today, update the steps
                stepLogDao.updateStepCountForUserAndDate(userId, date, steps)
                tvSteps.text = steps.toString()
            }
        }
    }

    private fun loadData() {
        val date = getTodayDate()
        // Retrieve userId from fragment arguments
        val userId = arguments?.getInt("userId", 0) ?: 0
        lifecycleScope.launch {
            val stepLog = stepLogDao.getStepLogForUserAndDate(userId, date)
            if (stepLog != null) {
                val savedSteps = stepLog.steps
                steps = savedSteps
                tvSteps.text = steps.toString()
            }
        }
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }
}