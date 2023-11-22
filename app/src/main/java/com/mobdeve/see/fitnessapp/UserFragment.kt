package com.mobdeve.see.fitnessapp

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.mobdeve.see.fitnessapp.databinding.FragmentUserBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UserFragment: Fragment()  {
    private lateinit var stepGoalSeekBar: SeekBar
    private lateinit var stepSetValueEditText: EditText
    private lateinit var userDao: UserDao
    private lateinit var stepLogDao: StepLogDao

    private lateinit var btnLogout: Button
    private lateinit var btnDeleteProfile: Button

    private lateinit var applicationContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)

        applicationContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentUserBinding = FragmentUserBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stepGoalSeekBar = view.findViewById(R.id.stepGoalSeekBar)
        stepSetValueEditText = view.findViewById(R.id.etnStepValue)
        btnLogout = view.findViewById(R.id.logout)
        btnDeleteProfile = view.findViewById(R.id.delete)



        val appDatabase = AppDatabase.getDatabase(requireContext())
        userDao = appDatabase.userDao()
        stepLogDao = appDatabase.stepLogDao()

        stepSetValueEditText.setText("${stepGoalSeekBar.progress}")

        stepSetValueEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, after: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                stepGoalSeekBar.progress = s?.toString()?.toIntOrNull() ?: 0
            }
        })

        stepGoalSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                stepSetValueEditText.setText("$progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        val saveButton = view.findViewById<Button>(R.id.btnSave)
        saveButton.setOnClickListener {
            val stepGoal = stepGoalSeekBar.progress
            val date = getTodayDate()
            val userId = arguments?.getInt("userId", 0) ?: 0

            lifecycleScope.launch {
                val stepLog = stepLogDao.getStepLogForUserAndDate(userId, date)
                if (stepLog != null) {
                    stepLogDao.updateStepGoalForUserAndDate(userId, date, stepGoal)
                }
            }

            Toast.makeText(requireContext(), "Saved!", Toast.LENGTH_SHORT).show()
        }

        btnDeleteProfile.setOnClickListener{
            val intent = Intent(applicationContext, DeleteProfileActivity::class.java)
            val userId = arguments?.getInt("userId", 0) ?: 0
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val intent = Intent(applicationContext, LogOutActivity::class.java)
            val userId = arguments?.getInt("userId", 0) ?: 0
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }
}