package com.mobdeve.see.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.see.fitnessapp.databinding.*

class DeleteProfileActivity : AppCompatActivity() {
    private lateinit var yesButton: Button
    private lateinit var noButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityDeleteProfileBinding = ActivityDeleteProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnDeleteYes.setOnClickListener (View.OnClickListener {

        })

        viewBinding.btnDeleteNo.setOnClickListener (View.OnClickListener {

        })
    }

}