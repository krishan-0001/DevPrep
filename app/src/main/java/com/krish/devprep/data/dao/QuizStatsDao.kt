package com.krish.devprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krish.devprep.data.local.QuizStatsEntity

@Dao
interface QuizStatsDao {

    @Query("SELECT * FROM quiz_stats WHERE id = 1")
    suspend fun getStats(): QuizStatsEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertStats(stats: QuizStatsEntity)

}