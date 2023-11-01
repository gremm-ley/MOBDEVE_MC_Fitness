package com.mobdeve.see.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mobdeve.see.fitnessapp.databinding.*
import kotlinx.coroutines.launch

class DeleteProfileActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    private lateinit var stepLogDao: StepLogDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityDeleteProfileBinding = ActivityDeleteProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val appDatabase = AppDatabase.getDatabase(this)
        userDao = appDatabase.userDao()
        stepLogDao = appDatabase.stepLogDao()

        val userId = intent.getIntExtra("userId", 0)

        viewBinding.btnDeleteYes.setOnClickListener (View.OnClickListener {
            Log.d("UserIdMessage", userId.toString())
            lifecycleScope.launch {
                val user = userDao.getUserById(userId)
                if (user != null) {
                    Log.d("DeleteMessage", "Got in here")
                    userDao.deleteUser(user)
                    stepLogDao.deleteUserStepLogs(userId)
                }

                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        })

        viewBinding.btnDeleteNo.setOnClickListener (View.OnClickListener {
            finish()
        })
    }

}