package com.mobdeve.see.fitnessapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.see.fitnessapp.databinding.ActivitySetGoalBinding
import com.mobdeve.see.fitnessapp.databinding.ActivityStepCounterBinding
import kotlin.math.roundToInt

class SetGoalActivity : AppCompatActivity() {

    private lateinit var stepGoalSeekBar: SeekBar
    private lateinit var stepGoalValueTextView: TextView
    private lateinit var stepSetValueEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySetGoalBinding = ActivitySetGoalBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        stepGoalSeekBar = findViewById(R.id.stepGoalSeekBar)
        //stepGoalValueTextView = findViewById(R.id.tvStepGoalValue)
        stepSetValueEditText = findViewById(R.id.etnStepValue)



        // Set an initial value for the step goal text view
        //stepGoalValueTextView.text = "${stepGoalSeekBar.progress} steps"
        stepSetValueEditText.setText("${stepGoalSeekBar.progress}")

        // Set a listener to update the step goal text view when the seek bar is changed

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
                // Not needed for this example
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not needed for this example
            }
        })

        // Implement functionality to save the step goal when the "Save" button is clicked
        val saveButton = findViewById<Button>(R.id.btnSave)
        saveButton.setOnClickListener {
            val stepGoal = stepGoalSeekBar.progress
            // Save the step goal to the database or preferences
            // You can add your code here to save the user's step goal

            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()

        }
    }
}