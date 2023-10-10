package com.mobdeve.see.fitnessapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.see.fitnessapp.databinding.ActivityMapBinding
import com.mobdeve.see.fitnessapp.databinding.ActivityStatsBinding

class StatsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityStatsBinding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}