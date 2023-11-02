package com.mobdeve.see.fitnessapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mobdeve.see.fitnessapp.databinding.ActivitySetGoalBinding
import com.mobdeve.see.fitnessapp.databinding.ActivityStepCounterBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

class SetGoalActivity : AppCompatActivity() {

    private lateinit var stepGoalSeekBar: SeekBar
    private lateinit var stepGoalValueTextView: TextView
    private lateinit var stepSetValueEditText: EditText
    private lateinit var userDao: UserDao
    private lateinit var stepLogDao: StepLogDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySetGoalBinding = ActivitySetGoalBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        stepGoalSeekBar = findViewById(R.id.stepGoalSeekBar)
        //stepGoalValueTextView = findViewById(R.id.tvStepGoalValue)
        stepSetValueEditText = findViewById(R.id.etnStepValue)


        val appDatabase = AppDatabase.getDatabase(this)
        userDao = appDatabase.userDao()
        stepLogDao = appDatabase.stepLogDao()
        //stepGoalValueTextView.text = "${stepGoalSeekBar.progress} steps"
        stepSetValueEditText.setText("${stepGoalSeekBar.progress}")


        stepSetValueEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, after: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                stepGoalSeekBar.progress = Integer.parseInt(s.toString())
            }
        })

        stepGoalSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //stepGoalValueTextView.text = "$progress steps"
                stepSetValueEditText.setText("$progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        // Implement functionality to save the step goal when the "Save" button is clicked
        val saveButton = findViewById<Button>(R.id.btnSave)
        saveButton.setOnClickListener {
            val stepGoal = stepGoalSeekBar.progress
            val date = getTodayDate()
            val userId = intent.getIntExtra("userId", 0)
            lifecycleScope.launch {
                val stepLog = stepLogDao.getStepLogForUserAndDate(userId, date)
                if (stepLog != null) {
                    stepLogDao.updateStepGoalForUserAndDate(userId, date, stepGoal)
                }
            }

            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }
}