package com.example.devprep.data.local

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuestionViewModelFactory(
    private val dao: QuestionDao,
    private val quizStatsDao: QuizStatsDao,
    private val categoryStatsDao: CategoryStatsDao,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionViewModel(dao, quizStatsDao, categoryStatsDao, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
