package com.krish.devprep.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.krish.devprep.data.local.JsonLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {
    @Volatile
    private var instance: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "devprep_db"
            ).fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val database = instance!!
                        database.questionDao().insertAll(JsonLoader.loadQuestions(context))
                        database.guideDao().insertAll(JsonLoader.loadGuides(context))
                        database.codingDao().insertAll(JsonLoader.loadCodingQuestions(context))
                    }
                }
            }).build().also { instance = it }
        }
    }
}