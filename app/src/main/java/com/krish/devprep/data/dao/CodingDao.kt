package com.krish.devprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krish.devprep.data.local.CodingQuestionEntity

@Dao
interface CodingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<CodingQuestionEntity>)

    @Query("SELECT * FROM coding_questions WHERE category = :category")
    fun getByCategory(category: String): List<CodingQuestionEntity>
    @Query("SELECT * FROM coding_questions")
    suspend fun getAll(): List<CodingQuestionEntity>

    @Query("UPDATE coding_questions SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: Int, isBookmarked: Boolean)

    @Query("SELECT COUNT(*) FROM coding_questions")
    suspend fun getCount(): Int

    @Query("DELETE FROM coding_questions")
    suspend fun clearAll()

}
