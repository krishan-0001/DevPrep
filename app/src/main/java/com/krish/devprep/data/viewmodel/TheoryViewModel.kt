package com.krish.devprep.data.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krish.devprep.data.Theory
import com.krish.devprep.data.dao.TheoryDao
import com.krish.devprep.data.local.JsonLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TheoryViewModel(private val dao: TheoryDao,private val context: Context) : ViewModel() {

   var theoryList by mutableStateOf<List<Theory>>(emptyList())
       private set
     fun loadTheory(category: String) {
        viewModelScope.launch {
            val existing = withContext(Dispatchers.IO){
                dao.getAllTheory()
            }
            if(existing.isEmpty()){
                val data = JsonLoader.loadTheory(context)
                withContext(Dispatchers.IO){
                    dao.insertTheory(data)
                }
            }
            val result = withContext(Dispatchers.IO){
                dao.getTheoryByCategory(category.trim())
            }
            theoryList = result
    }

}
    suspend fun getTheoryById(id: Int): Theory{
        return withContext(Dispatchers.IO){
            dao.getTheoryById(id)

        }
    }
}