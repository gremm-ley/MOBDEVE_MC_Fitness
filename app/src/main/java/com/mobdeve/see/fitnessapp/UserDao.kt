package com.mobdeve.see.fitnessapp

import androidx.room.*
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE user_email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE user_email = :email AND user_password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
    // Add more methods for updating user details if needed
}