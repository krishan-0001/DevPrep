package com.example.devprep.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {
    fun provideDatabase(context: Context): AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "devprep_database"
        ).addCallback(object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase){
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch{
                    val database = provideDatabase(context)
                    database.questionDao().insertAll(QuestionSeed.questions)
                }

            }
        }).build()
    }
}