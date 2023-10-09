package com.mobdeve.see.fitnessapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.see.fitnessapp.databinding.ActivityStepCounterBinding
import com.mobdeve.see.fitnessapp.databinding.ActivityUserProfileBinding

class StepsCounterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityStepCounterBinding = ActivityStepCounterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

    }
}