package com.krish.devprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.krish.devprep.data.local.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
     @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
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
    @Query("SELECT COUNT(*) FROM questions WHERE isAttempted = 1")
    suspend fun getAttemptedCount(): Int
    @Query("UPDATE questions SET isAttempted = 1, isCorrect = :isCorrect WHERE id = :questionId")
    suspend fun updateAnswer(questionId: Int, isCorrect: Boolean)
    @Query("SELECT COUNT(*) FROM questions WHERE isCorrect = 1")
    suspend fun getScore(): Int
    @Query("SELECT isAttempted FROM questions WHERE id = :id")
    suspend fun isQuestionAttempted(id: Int): Boolean
    @Query("DELETE FROM questions")
    suspend fun clearAll()
}