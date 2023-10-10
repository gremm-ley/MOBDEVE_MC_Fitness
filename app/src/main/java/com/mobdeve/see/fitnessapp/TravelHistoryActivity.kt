package com.mobdeve.see.fitnessapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.see.fitnessapp.databinding.ActivityStatsBinding
import com.mobdeve.see.fitnessapp.databinding.ActivityTravelHistoryBinding

class TravelHistoryActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityTravelHistoryBinding = ActivityTravelHistoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}