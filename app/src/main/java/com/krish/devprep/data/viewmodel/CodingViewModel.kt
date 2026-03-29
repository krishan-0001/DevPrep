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
import com.krish.devprep.ui.filter.Difficulty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.emptyList

class CodingViewModel(
    private val dao: CodingDao,
    private val context: Context
) : ViewModel(){

    private val _questions = MutableStateFlow<List<CodingQuestionEntity>>(emptyList())
    val questions: StateFlow<List<CodingQuestionEntity>> = _questions
    //val questions = mutableStateListOf<CodingQuestionEntity>()

    private val _selectedDifficulty = MutableStateFlow(Difficulty.ALL)
    val selectedDifficulty : StateFlow<Difficulty> = _selectedDifficulty

    val filteredQuestions = combine(_questions,_selectedDifficulty){list,difficulty->

        when(difficulty){
            Difficulty.ALL -> list
            else -> list.filter{
                it.difficulty.equals(difficulty.name, ignoreCase = true)
            }
        }

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )
    fun setDifficulty(difficulty: Difficulty){
        _selectedDifficulty.value = difficulty
    }

    fun loadQuestions(category: String){

        viewModelScope.launch(Dispatchers.IO) {
            val cleanCategory = category.trim()
                val data = JsonLoader.loadCodingQuestions(context)
                 //dao.clearAll()
                dao.insertAll(data)
            val result = if (cleanCategory == "Coding") {
                dao.getAll()
            } else {
                dao.getByCategory(cleanCategory)
            }
           // val result = dao.getAll()
            _questions.value = result
        }
    }

    fun toggleBookmark(question: CodingQuestionEntity){
        viewModelScope.launch {
            val newState = !question.isBookmarked
            dao.updateBookmark(question.id, newState)
            val updatedList = _questions.value.map {
                if(it.id == question.id){
                    it.copy(isBookmarked = newState)
                }
                else it
            }
            _questions.value = updatedList
        }
    }
}
