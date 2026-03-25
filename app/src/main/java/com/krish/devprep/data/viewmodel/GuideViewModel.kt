package com.krish.devprep.data.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krish.devprep.data.dao.GuideDao
import com.krish.devprep.data.local.GuideEntity
import com.krish.devprep.data.local.JsonLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GuideViewModel(
    private val dao: GuideDao,
    private val context: Context) : ViewModel() {

    val guides = mutableStateListOf<GuideEntity>()

    fun loadGuides() {
        viewModelScope.launch(Dispatchers.IO) {
            if (dao.getAllGuides().isEmpty()) {
                val data = JsonLoader.loadGuides(context)
                dao.insertAll(data)
            }
            val result = dao.getAllGuides()
            withContext(Dispatchers.Main){
                guides.clear()
                guides.addAll(result)
            }

        }
    }
}


