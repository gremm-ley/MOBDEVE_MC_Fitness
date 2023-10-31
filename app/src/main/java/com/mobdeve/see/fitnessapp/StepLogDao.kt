package com.mobdeve.see.fitnessapp

import androidx.room.*

@Dao
interface StepLogDao {
    @Query("SELECT * FROM step_logs WHERE userId = :userId")
    suspend fun getStepLogsForUser(userId: Int): List<StepLog>

    @Insert
    suspend fun insertStepLog(stepLog: StepLog)

    // Add methods to delete logs, retrieve logs for a specific date, etc., as needed
}