package com.krish.devprep.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krish.devprep.data.dao.CategoryStatsDao
import com.krish.devprep.data.dao.CodingDao
import com.krish.devprep.data.dao.GuideDao
import com.krish.devprep.data.dao.QuestionDao
import com.krish.devprep.data.dao.QuizStatsDao
import com.krish.devprep.data.local.CategoryStatsEntity
import com.krish.devprep.data.local.CodingQuestionEntity
import com.krish.devprep.data.local.Converters
import com.krish.devprep.data.local.GuideEntity
import com.krish.devprep.data.local.QuestionEntity
import com.krish.devprep.data.local.QuizStatsEntity

@Database(
    entities = [QuestionEntity::class,
               QuizStatsEntity::class,
               CategoryStatsEntity::class,
               GuideEntity::class,
               CodingQuestionEntity::class],
    version = 6
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun questionDao(): QuestionDao
    abstract fun quizStatsDao(): QuizStatsDao
    abstract fun categoryStatsDao(): CategoryStatsDao
    abstract fun guideDao(): GuideDao
    abstract fun codingDao(): CodingDao


    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "devprep_db"
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