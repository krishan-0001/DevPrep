package com.krish.devprep.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizStatsDao {

    @Query("SELECT * FROM quiz_stats WHERE id = 1")
    suspend fun getStats(): QuizStatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(stats: QuizStatsEntity)

}