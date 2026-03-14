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

class QuestionViewModel(private val dao: QuestionDao,private val context: Context) : ViewModel() {
    var questions = mutableStateListOf<QuestionEntity>()
        private set
    var score by mutableStateOf(0)
        private set
    private val answeredQuestions = mutableSetOf<Int>()
    val bookmarkedQuestions = dao.getBookmarkedQuestions()

    fun loadQuestions(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            if(dao.getQuestionCount()==0){
                val loadedQuestions = JsonLoader.loadQuestions(context)
                dao.insertAll(loadedQuestions)
            }
            val result = dao.getQuestionsByCategory(category)
            withContext(Dispatchers.Main){
                questions.clear()
                questions.addAll(result)
            }
        }

    }
    fun onCheckAnswer(selectedOption: Int, question: QuestionEntity) {
        if(answeredQuestions.contains(question.id)){
            return
        }
        if (selectedOption == question.correctAnswer) {
            score+=10
        }
        answeredQuestions.add(question.id)
    }
    fun toggleBookmark(question: QuestionEntity){
        viewModelScope.launch {
            val newState = !question.isBookmarked
                dao.updateBookmark(
                    questionId = question.id,
                    bookmarked = newState
                )
            val index = questions.indexOf(question)
            if(index!=-1){
                questions[index] = question.copy(isBookmarked = newState)
            }


        }
    }


}