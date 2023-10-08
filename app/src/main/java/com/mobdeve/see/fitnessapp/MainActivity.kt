package com.mobdeve.see.fitnessapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.mobdeve.see.fitnessapp.databinding.*

class MainActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnLogin.setOnClickListener (View.OnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            this.startActivity(intent)
        })

        viewBinding.btnRegister.setOnClickListener (View.OnClickListener {
            val intent = Intent(applicationContext, Register::class.java)
            this.startActivity(intent)
        })
    }

}