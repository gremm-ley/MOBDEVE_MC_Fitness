package com.mobdeve.see.fitnessapp

import androidx.room.*

@Entity(tableName = "step_logs")
data class StepLog(
    @PrimaryKey(autoGenerate = true) val logId: Int,
    val userId: Int,
    val steps: Int,
    val stepGoal: Int,
    val date: Long // Store date as Unix timestamp
)