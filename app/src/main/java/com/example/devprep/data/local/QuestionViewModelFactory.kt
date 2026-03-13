package com.example.devprep.data.local

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuestionViewModelFactory(
    private val dao: QuestionDao,
    private val context: Context) : ViewModelProvider.Factory{

    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(QuestionViewModel::class.java)){
            return QuestionViewModel(dao,context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

