package com.krish.devprep.data.local

class QuestionRepository(private val dao: QuestionDao) {

    suspend fun getQuestionsByCategory(category: String) = dao.getQuestionsByCategory(category)
    suspend fun getBookmarks() = dao.getBookmarkedQuestions()
    suspend fun insertAll(question: List<QuestionEntity>) = dao.insertAll(question)
    suspend fun updateQuestion(question: QuestionEntity) = dao.updateQuestion(question)
    suspend fun updateBookmark(question: QuestionEntity) {
        dao.updateBookmark(question.id, !question.isBookmarked)
    }
}


