package com.mobdeve.see.fitnessapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.see.fitnessapp.databinding.ActivityMapBinding
import com.mobdeve.see.fitnessapp.databinding.ActivityStepCounterBinding

class MapActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityMapBinding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}