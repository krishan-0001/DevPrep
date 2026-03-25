package com.krish.devprep.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krish.devprep.data.dao.CodingDao
import com.krish.devprep.data.local.CodingQuestionEntity
import com.krish.devprep.data.local.JsonLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CodingViewModel(
    private val dao: CodingDao,
    private val context: Context
) : ViewModel(){

    val questions = mutableListOf<CodingQuestionEntity>()

    fun loadQuestions(category: String){

        viewModelScope.launch(Dispatchers.IO) {
            if(dao.getCount() ==0){
                val data = JsonLoader.loadQuestions(context)
                dao.insertAll(data)
            }
            val result = dao.getByCategory(category)
            withContext(Dispatchers.Main){
                questions.clear()
                questions.addAll(result)
            }
        }
    }

    fun toggleBookmark(question: CodingQuestionEntity){
        viewModelScope.launch {
            val newState = !question.isBookmarked
            dao.updateBookmark(question.id,newState)
            val index = questions.indexOfFirst { it.id == question.id }
            if(index!=-1){
                questions[index] = question.copy(isBookmarked = newState)

            }
        }
    }
}