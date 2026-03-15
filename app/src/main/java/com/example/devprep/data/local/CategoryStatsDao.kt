package com.example.devprep.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryStatsDao {

    @Query("SELECT * FROM category_stats")
    suspend fun getAllStats(): List<CategoryStatsEntity>

    @Query("SELECT * FROM category_stats WHERE category = :category")
    suspend fun getStats(category: String): CategoryStatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(stats: CategoryStatsEntity)
}