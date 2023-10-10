package com.mobdeve.see.fitnessapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.see.fitnessapp.databinding.ActivitySetGoalBinding
import com.mobdeve.see.fitnessapp.databinding.ActivityStepCounterBinding

class SetGoalActivity : AppCompatActivity() {

    private lateinit var stepGoalSeekBar: SeekBar
    private lateinit var stepGoalValueTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySetGoalBinding = ActivitySetGoalBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        stepGoalSeekBar = findViewById(R.id.stepGoalSeekBar)
        stepGoalValueTextView = findViewById(R.id.textViewStepGoalValue)

        // Set an initial value for the step goal text view
        stepGoalValueTextView.text = "${stepGoalSeekBar.progress} steps"

        // Set a listener to update the step goal text view when the seek bar is changed
        stepGoalSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                stepGoalValueTextView.text = "$progress steps"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not needed for this example
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not needed for this example
            }
        })

        // Implement functionality to save the step goal when the "Save" button is clicked
        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val stepGoal = stepGoalSeekBar.progress
            // Save the step goal to the database or preferences
            // You can add your code here to save the user's step goal
        }
    }
}