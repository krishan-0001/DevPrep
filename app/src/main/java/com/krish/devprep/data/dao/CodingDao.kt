package com.krish.devprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.krish.devprep.data.local.CodingQuestionEntity
import com.krish.devprep.data.local.QuestionEntity

@Dao
interface CodingDao{

    @Insert
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Query("SELECT * FROM coding_Questions WHERE category = :category")
    suspend fun getByCategory(category: String): List<CodingQuestionEntity>
    @Query("UPDATE coding_questions SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: Int, isBookmarked: Boolean)
    @Query("SELECT COUNT(*) FROM coding_questions")
    suspend fun getCount(): Int

}

