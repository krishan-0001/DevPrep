package com.example.devprep.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAll(questions: List<QuestionEntity>)

     @Query("SELECT * FROM questions WHERE category = :category")
    suspend fun getQuestionsByCategory(category: String): List<QuestionEntity>

     @Query("SELECT * FROM questions WHERE isBookmarked = 1")
     fun getBookmarkedQuestions(): Flow<List<QuestionEntity>>
     @Query("UPDATE questions SET isBookmarked = :bookmarked WHERE id = :questionId")
     suspend fun updateBookmark(questionId: Int, bookmarked: Boolean)

     @Update
     suspend fun updateQuestion(question: QuestionEntity)
     @Query("SELECT COUNT(*) FROM questions")
     suspend fun getQuestionCount(): Int
}