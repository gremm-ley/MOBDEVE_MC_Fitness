package com.mobdeve.see.fitnessapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.see.fitnessapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    // Replace with a more secure storage method for production
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val loginButton: Button = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                /*val storedEmail = sharedPreferences.getString("email", "")
                val storedPassword = sharedPreferences.getString("password", "")

                if (email == storedEmail && password == storedPassword) {
                    // Success
                    startActivity(Intent(this, UserProfileActivity::class.java))
                } else {
                    // Failure
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }*/
                val intent = Intent(applicationContext, UserProfileActivity::class.java)
                this.startActivity(intent)
                finish()
            }
        }
    }
}