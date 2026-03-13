package com.example.devprep.data.local


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionViewModel(private val dao: QuestionDao,private val context: Context) : ViewModel() {
    var questions by mutableStateOf<List<QuestionEntity>>(emptyList())
        private set
    fun loadQuestions(category: String){
        viewModelScope.launch {
            val result: List<QuestionEntity> = withContext(Dispatchers.IO){
                if(dao.getQuestionCount()==0){
                    val questions = JsonLoader.loadQuestions(context)
                    dao.insertAll(questions)
                }
                dao.getQuestionsByCategory(category)
            }
            questions = result

        }

    }
    fun toggleBookmark(question: QuestionEntity){
        viewModelScope.launch {
            val newState = !question.isBookmarked
            withContext(Dispatchers.IO){
                dao.updateBookmark(
                    questionId = question.id,
                    bookmarked = newState
                )
            }
            questions = questions.map{
                if(it.id==question.id){
                    it.copy(isBookmarked = newState)
                }
                else{
                    it
                }
            }

        }
    }


}