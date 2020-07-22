package com.example.refactoringproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserLogDAO {
    @Query("SELECT log from userLog where userId = :userId")
    fun getAll(userId: String): List<String>

    @Query("select log from userLog where userId = :userId limit 7")
    fun getRecentLog(userId: String): List<String>

    @Query("delete from userLog where userId = :userId")
    fun deleteLog(userId: String)

    @Insert
    fun insert(userLog: UserLog)

}