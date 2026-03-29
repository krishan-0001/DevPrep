package com.krish.devprep.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krish.devprep.data.dao.HrDao
import com.krish.devprep.data.local.HrEntity
import com.krish.devprep.data.local.JsonLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HrViewModel(
    private val dao: HrDao,
    private val context: Context
) : ViewModel(){

    private val _questions = MutableStateFlow<List<HrEntity>>(emptyList())
    val questions : StateFlow<List<HrEntity>> = _questions

    fun loadQuestions(category: String){
        viewModelScope.launch(Dispatchers.IO) {

            val data = JsonLoader.loadHr(context)
            dao.insertAll(data)
            val result = if(category=="HR"){
                dao.getAll()
            }else{
                dao.getQuestionsByCategory(category)
            }
            _questions.value = result
        }
    }

    fun toggleBookmark(question: HrEntity){
        viewModelScope.launch {
            val newState = !question.isBookmarked
            dao.updateBookmark(question.id, newState)

            _questions.value = _questions.value.map {
                if(it.id==question.id){
                    it.copy(isBookmarked = newState)
                }else{
                    it
                }
            }

        }
    }

}