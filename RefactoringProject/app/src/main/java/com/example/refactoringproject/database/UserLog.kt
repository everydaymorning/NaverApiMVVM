package com.example.refactoringproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userLog")
data class UserLog(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var userId: String? = null,
    val log: String
)