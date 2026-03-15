package com.example.devprep.data.local


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionViewModel(private val dao: QuestionDao,
                        private val quizStatsDao: QuizStatsDao,
                        private val categoryStatsDao: CategoryStatsDao,
                        private val context: Context) : ViewModel() {
    var questions = mutableStateListOf<QuestionEntity>()
        private set
    var score by mutableStateOf(0)
        private set
    private val answeredQuestions = mutableSetOf<Int>()
    var quizStats by mutableStateOf<QuizStatsEntity?>(null)
        private set
    val bookmarkedQuestions = dao.getBookmarkedQuestions()
    var categoryStats by mutableStateOf<List<CategoryStatsEntity>>(emptyList())
        private set
    fun loadCategoryStats(){
        viewModelScope.launch(Dispatchers.IO) {
            categoryStats = categoryStatsDao.getAllStats()
        }
    }

    fun loadStats(){
        viewModelScope.launch(Dispatchers.IO) {

            var stats = quizStatsDao.getStats()
            if (stats == null) {

                stats = QuizStatsEntity(
                    id = 1,
                    quizzesAttempted = 0,
                    totalQuestions = 0,
                    totalScore = 0
                )

                quizStatsDao.insertStats(stats)
            }

            quizStats = stats

        }
    }

    fun loadQuestions(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (dao.getQuestionCount() == 0) {
                val loadedQuestions = JsonLoader.loadQuestions(context)
                dao.insertAll(loadedQuestions)
            }
            val result = dao.getQuestionsByCategory(category)
            withContext(Dispatchers.Main) {
                questions.clear()
                questions.addAll(result)
            }
        }

    }

    fun onCheckAnswer(selectedOption: Int, question: QuestionEntity) {
        if (answeredQuestions.contains(question.id)) {
            return
        }
        val isCorrect = selectedOption == question.correctAnswer
        if (isCorrect) {
            score += 10
        }
        answeredQuestions.add(question.id)
        updateCategoryStats(question.category,isCorrect)
    }

    fun toggleBookmark(question: QuestionEntity) {
        viewModelScope.launch {
            val newState = !question.isBookmarked
            dao.updateBookmark(
                questionId = question.id,
                bookmarked = newState
            )
            val index = questions.indexOf(question)
            if (index != -1) {
                questions[index] = question.copy(isBookmarked = newState)
            }


        }
    }

    fun updateQuizStats(totalQuestions: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val stats = quizStatsDao.getStats()
            if(stats==null){
                quizStatsDao.insertStats(
                    QuizStatsEntity(
                        id = 1,
                        quizzesAttempted = 1,
                        totalScore = score,
                        totalQuestions = totalQuestions

                    )
                )
            }
            else{
                quizStatsDao.insertStats(
                    QuizStatsEntity(
                        id = 1,
                        quizzesAttempted = stats.quizzesAttempted+1,
                        totalScore = stats.totalScore+score,
                        totalQuestions = stats.totalQuestions+totalQuestions

                    )
                    )
            }
        }


    }

    fun updateCategoryStats(category: String, correct: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val stats = categoryStatsDao.getStats(category)
            if (stats == null) {
                categoryStatsDao.insertStats(
                    CategoryStatsEntity(
                        category = category,
                        totalQuestions = 1,
                        correctAnswers = if(correct) 1 else 0
                    )
                )
            }
            else{
                categoryStatsDao.insertStats(
                    CategoryStatsEntity(
                        category = category,
                        totalQuestions = stats.totalQuestions+1,
                        correctAnswers = stats.correctAnswers + if(correct) 1 else 0

                    )
                )
            }

        }
    }

    fun resetQuiz(){
        score =0
        answeredQuestions.clear()
    }



}
