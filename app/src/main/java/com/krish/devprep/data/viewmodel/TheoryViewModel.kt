package com.krish.devprep.data.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krish.devprep.data.Theory
import com.krish.devprep.data.dao.TheoryDao
import kotlinx.coroutines.launch

class TheoryViewModel(private val dao: TheoryDao) : ViewModel() {

   var theoryList by mutableStateOf<List<Theory>>(emptyList())
       private set
     fun loadTheory(category: String) {
        viewModelScope.launch {
            theoryList = dao.getTheoryByCategory(category)
    }

}
    suspend fun getTheoryById(id: Int): Theory{
        return dao.getTheoryById(id)
    }
}