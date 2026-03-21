package com.example.devprep.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.devprep.data.local.AppDatabase
import com.example.devprep.data.local.QuestionDao
import com.example.devprep.data.local.QuestionViewModel
import com.example.devprep.data.local.QuestionViewModelFactory

@Composable
fun QuestionScreen(
    category: String,
    navController: NavHostController,
    viewModel: QuestionViewModel
) {

    LaunchedEffect(category) {
        viewModel.loadQuestions(category)
    }

    val questions = viewModel.questions
    var currentIndex by remember {
        mutableStateOf(0)
    }
    if(questions.isNotEmpty()){
         val question = questions.getOrNull(currentIndex)
        if(question!=null){
            QuestionContent(question = question,
                questionNumber = currentIndex + 1,
                totalQuestions = questions.size,
                onBookmarkClick = {
                    viewModel.toggleBookmark(question)
                }, onNextClick = {
                    if (currentIndex < questions.size - 1) {
                        currentIndex++
                    } else {
                        val finalScore = viewModel.currentQuizScore
                        viewModel.updateQuizStats(totalQuestions = questions.size, score = finalScore)
                        viewModel.resetQuiz()
                        navController.navigate("results/${finalScore}/${questions.size}"){
                            popUpTo("questions"){inclusive=true}
                        }
                    }
                },
                onCheckAnswer = { selectedOption, question ->
                    viewModel.onCheckAnswer(selectedOption, question)
                })
        }
    }

}

