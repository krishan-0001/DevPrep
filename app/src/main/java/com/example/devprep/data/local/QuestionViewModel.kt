package com.example.devprep.data.local


import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
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
        //println("ANSWERED: ${question.id}  SCORE: $score")
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

    fun updateQuizStats(totalQuestions: Int,score: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            // LOCAL DATABASE(ROOM)
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

            // FIREBASE DATABASE(FIRESTORE)
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val db = FirebaseFirestore.getInstance()

            uid?.let{
                db.collection("users")
                    .document(it)
                    .update(mapOf(
                        "score" to FieldValue.increment(score.toLong()),
                        "quizzesAttempted" to FieldValue.increment(1),
                        "totalQuestions" to FieldValue.increment(totalQuestions.toLong())
                      )
                    )
                    .addOnSuccessListener {
                        Log.d("Firestore", "Stats updated")
                    }
                    .addOnFailureListener {
                        Log.e("Firestore", "Error updating stats: ${it.message}")
                    }
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
