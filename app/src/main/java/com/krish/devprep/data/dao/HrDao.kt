package com.krish.devprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krish.devprep.data.local.HrEntity

@Dao
interface HrDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<HrEntity>)

    @Query("SELECT * FROM hr_questions WHERE category = :category")
    suspend fun getQuestionsByCategory(category: String): List<HrEntity>

    @Query("SELECT * FROM hr_questions")
    suspend fun getAll(): List<HrEntity>

    @Query("UPDATE hr_questions SET isBookmarked = :state WHERE id = :id")
    suspend fun updateBookmark(id: Int, state: Boolean)


}