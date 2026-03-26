package com.krish.devprep.data.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krish.devprep.data.dao.CodingDao
import com.krish.devprep.data.local.CodingQuestionEntity
import com.krish.devprep.data.local.JsonLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.mutableStateListOf

class CodingViewModel(
    private val dao: CodingDao,
    private val context: Context
) : ViewModel(){

    val questions = mutableStateListOf<CodingQuestionEntity>()


    fun loadQuestions(category: String){

        viewModelScope.launch(Dispatchers.IO) {
            val count = dao.getCount()
            Log.d("DEBUG", "Total Coding Questions: $count")
            if(count == 0){
                val data = JsonLoader.loadCodingQuestions(context)
                Log.d("DEBUG", "Loaded JSON size: ${data.size}")
                dao.insertAll(data)
            }
            
            val result = if (category == "Coding") {
                dao.getAll()
            } else {
                dao.getByCategory(category)
            }
            
            Log.d("DEBUG", "Category: $category, Result size: ${result.size}")
            withContext(Dispatchers.Main){
                questions.clear()
                questions.addAll(result)
            }
        }
    }

    fun toggleBookmark(question: CodingQuestionEntity){
        viewModelScope.launch {
            val newState = !question.isBookmarked
            dao.updateBookmark(question.id, newState)
            val index = questions.indexOfFirst { it.id == question.id }
            if(index != -1){
                questions[index] = question.copy(isBookmarked = newState)
            }
        }
    }
}
