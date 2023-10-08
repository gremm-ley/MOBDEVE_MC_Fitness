package com.mobdeve.see.fitnessapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set click listeners for login and register buttons
        val loginButton = findViewById<Button>(R.id.mainLoginButton)
        val registerButton = findViewById<Button>(R.id.mainRegisterButton)

        loginButton.setOnClickListener {
            // Navigate to the Login Activity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            // Navigate to the Register Activity
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}