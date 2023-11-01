package com.mobdeve.see.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mobdeve.see.fitnessapp.databinding.ActivityUserProfileBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class UserProfileActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    private lateinit var stepLogDao: StepLogDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityUserProfileBinding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val userFullName = intent.getStringExtra("userFullName") // Get user's full name
        val userId = intent.getIntExtra("userId", 0)
        viewBinding.textView.text = "Welcome, $userFullName"

        val appDatabase = AppDatabase.getDatabase(this)
        userDao = appDatabase.userDao()
        stepLogDao = appDatabase.stepLogDao()

        createNewStepLog(userId)

        viewBinding.btnStepCounter.setOnClickListener {
            val intent = Intent(applicationContext, StepsCounterActivity::class.java)
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }
        viewBinding.btnSetGoal.setOnClickListener{
            val intent = Intent(applicationContext, SetGoalActivity::class.java)
            this.startActivity(intent)
        }
        viewBinding.btnStats.setOnClickListener{
            val intent = Intent(applicationContext, StatsActivity::class.java)
            this.startActivity(intent)
        }
        viewBinding.btnStepHistory.setOnClickListener{
            val intent = Intent(applicationContext, TravelHistoryActivity::class.java)
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }
        viewBinding.btnDeleteProfile.setOnClickListener{
            val intent = Intent(applicationContext, DeleteProfileActivity::class.java)
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }
        viewBinding.btnLogout.setOnClickListener {
            finish()
        }
    }

    private fun createNewStepLog(userId: Int) {
        val date = getTodayDate()

        lifecycleScope.launch {
            val stepLog = stepLogDao.getStepLogForUserAndDate(userId, date)
            if (stepLog == null) {
                val newStepLog = StepLog(userId = userId, steps = 0, goal = 0, date = date)
                Log.d("stepLogMeessage", "Made it here " + newStepLog.toString())
                stepLogDao.insertStepLog(newStepLog)
            }
        }
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }
}
