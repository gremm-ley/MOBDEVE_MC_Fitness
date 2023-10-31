package com.mobdeve.see.fitnessapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.see.fitnessapp.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityUserProfileBinding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val userFullName = intent.getStringExtra("userFullName") // Get user's full name
        viewBinding.textView.text = "Welcome, $userFullName"

        viewBinding.btnStepCounter.setOnClickListener {
            val intent = Intent(applicationContext, StepsCounterActivity::class.java)
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
            this.startActivity(intent)
        }
        viewBinding.btnDeleteProfile.setOnClickListener{
            val intent = Intent(applicationContext, DeleteProfileActivity::class.java)
            this.startActivity(intent)
        }
        viewBinding.btnLogout.setOnClickListener {
            finish()
        }
    }
}