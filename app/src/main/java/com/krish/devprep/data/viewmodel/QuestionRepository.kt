package com.krish.devprep.data.viewmodel

import com.krish.devprep.data.dao.QuestionDao
import com.krish.devprep.data.local.QuestionEntity

class QuestionRepository(private val dao: QuestionDao) {

    suspend fun getQuestionsByCategory(category: String) = dao.getQuestionsByCategory(category)
    suspend fun getBookmarks() = dao.getBookmarkedQuestions()
    suspend fun insertAll(question: List<QuestionEntity>) = dao.insertAll(question)
    suspend fun updateQuestion(question: QuestionEntity) = dao.updateQuestion(question)
    suspend fun updateBookmark(question: QuestionEntity) {
        dao.updateBookmark(question.id, !question.isBookmarked)
    }
}