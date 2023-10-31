package com.mobdeve.see.fitnessapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.mobdeve.see.fitnessapp.databinding.*

class MainActivity : AppCompatActivity() {
    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = AppDatabase.getDatabase(this)
        val viewBinding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnLogin.setOnClickListener (View.OnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            this.startActivity(intent)
        })

        viewBinding.btnRegister.setOnClickListener (View.OnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            this.startActivity(intent)
        })
    }

}