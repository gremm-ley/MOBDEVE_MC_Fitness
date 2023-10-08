package com.mobdeve.see.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.mobdeve.see.fitnessapp.databinding.ActivityLoginBinding
import com.mobdeve.see.fitnessapp.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.registerButton.setOnClickListener {
            val intent = Intent(applicationContext, UserProfile::class.java)
            this.startActivity(intent)
        }
    }
}