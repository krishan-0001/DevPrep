package com.krish.devprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.krish.devprep.data.local.CodingQuestionEntity

@Dao
interface CodingDao{

    @Insert
    suspend fun insertAll(questions: List<CodingQuestionEntity>)

    @Query("SELECT * FROM coding_questions WHERE LOWER(category) = LOWER(:category)")
    suspend fun getByCategory(category: String): List<CodingQuestionEntity>

    @Query("SELECT * FROM coding_questions")
    suspend fun getAll(): List<CodingQuestionEntity>

    @Query("UPDATE coding_questions SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: Int, isBookmarked: Boolean)

    @Query("SELECT COUNT(*) FROM coding_questions")
    suspend fun getCount(): Int

}
