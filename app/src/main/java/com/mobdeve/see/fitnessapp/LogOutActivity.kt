package com.mobdeve.see.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mobdeve.see.fitnessapp.databinding.ActivityLogoutBinding
import kotlinx.coroutines.launch

class LogOutActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    private lateinit var stepLogDao: StepLogDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityLogoutBinding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val appDatabase = AppDatabase.getDatabase(this)
        userDao = appDatabase.userDao()
        stepLogDao = appDatabase.stepLogDao()

        val userId = intent.getIntExtra("userId", 0)

        viewBinding.btnLogoutYes.setOnClickListener (View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish the LogoutActivity
        })

        viewBinding.btnLogoutNo.setOnClickListener (View.OnClickListener {
            finish()
        })
    }

}