package com.mobdeve.see.fitnessapp

import androidx.room.*

@Dao
interface StepLogDao {
    @Query("SELECT steps, goal, date FROM step_logs WHERE userId = :userId")
    suspend fun getStepLogsForUser(userId: Int): List<StepLogWithGoal>
    @Query("SELECT * FROM step_logs WHERE userId = :userId AND date = :date")
    suspend fun getStepLogForUserAndDate(userId: Int, date: String): StepLog?
    @Query("UPDATE step_logs SET steps = :newStepCount WHERE userId = :userId AND date = :date")
    suspend fun updateStepCountForUserAndDate(userId: Int, date: String, newStepCount: Int)
    @Query("UPDATE step_logs SET goal = :newStepGoal WHERE userId = :userId AND date = :date")
    suspend fun updateStepGoalForUserAndDate(userId: Int, date: String, newStepGoal: Int)
    @Insert
    suspend fun insertStepLog(stepLog: StepLog)

    @Query("DELETE FROM step_logs WHERE userId = :userId")
    suspend fun deleteUserStepLogs(userId: Int)
}