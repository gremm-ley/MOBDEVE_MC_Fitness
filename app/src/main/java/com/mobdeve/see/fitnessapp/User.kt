package com.mobdeve.see.fitnessapp

import androidx.room.*

@Entity(tableName = "users")
data class User(
    @PrimaryKey @ColumnInfo(name = "user_id") val userId: Int = 0,
    @ColumnInfo(name = "user_name") val name: String?,
    @ColumnInfo(name = "user_email") val email: String?,
    @ColumnInfo(name = "user_password") val password: String?
)