package com.krish.devprep.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krish.devprep.data.dao.CategoryStatsDao
import com.krish.devprep.data.dao.CodingDao
import com.krish.devprep.data.dao.GuideDao
import com.krish.devprep.data.dao.QuestionDao
import com.krish.devprep.data.dao.QuizStatsDao
import com.krish.devprep.data.dao.TheoryDao
import kotlin.jvm.java

class AppViewModelFactory(
    private val questionDao: QuestionDao,
    private val quizStatsDao: QuizStatsDao,
    private val categoryStatsDao: CategoryStatsDao,
    private val guideDao: GuideDao,
    private val codingDao: CodingDao,
    private val theoryDao: TheoryDao,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when{
            modelClass.isAssignableFrom(QuestionViewModel::class.java) ->{
                QuestionViewModel(
                    questionDao,quizStatsDao,categoryStatsDao,context) as T
            }
            modelClass.isAssignableFrom(GuideViewModel::class.java) -> {
                GuideViewModel(
                    guideDao,
                    context
                ) as T
            }

            modelClass.isAssignableFrom(CodingViewModel::class.java) -> {
                CodingViewModel(
                    codingDao,
                    context
                ) as T
            }
            modelClass.isAssignableFrom(TheoryViewModel::class.java) -> {
                TheoryViewModel(
                    theoryDao
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}