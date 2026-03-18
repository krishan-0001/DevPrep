package com.example.devprep.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [QuestionEntity::class,
               QuizStatsEntity::class,
               CategoryStatsEntity::class],
    version = 3
)

abstract class AppDatabase: RoomDatabase(){
    abstract fun questionDao(): QuestionDao
    abstract fun quizStatsDao(): QuizStatsDao
    abstract fun categoryStatsDao(): CategoryStatsDao


    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "questions_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
       suspend fun clearDatabase(context: Context){
           val db = getDatabase(context)
           db.clearAllTables()
        }
    }
}