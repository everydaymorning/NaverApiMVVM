package com.example.refactoringproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.refactoringproject.constant.DBConstant

@Database(entities = [UserLog::class], version = 1)
abstract class UserLogDB: RoomDatabase(){
    abstract fun getUserLogDAO(): UserLogDAO

    companion object{
        private var INSTANCE: UserLogDB? = null

        fun getInstance(context: Context): UserLogDB{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserLogDB::class.java,
                    DBConstant.LOG_DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}