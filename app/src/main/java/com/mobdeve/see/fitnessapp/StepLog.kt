package com.mobdeve.see.fitnessapp

import androidx.room.*
import java.util.Date

@Entity(tableName = "step_logs")
data class StepLog(
    @PrimaryKey(autoGenerate = true) val logId: Int = 0,
    val userId: Int,
    val steps: Int,
    val goal: Int,
    val date: String // Store date as Unix timestamp
)