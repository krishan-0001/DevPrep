package com.krish.devprep.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.krish.devprep.data.local.QuestionViewModel
import kotlinx.coroutines.delay

@Composable
fun QuestionScreen(
    category: String,
    navController: NavHostController,
    viewModel: QuestionViewModel
) {

    val decodedCategory = java.net.URLDecoder.decode(category, "UTF-8")
    LaunchedEffect(category) {
        viewModel.loadQuestions(decodedCategory)
    }

    val questions = viewModel.questions
    var currentIndex by remember {
        mutableStateOf(0)
    }
    if(questions.isNotEmpty()){
         val question = questions.getOrNull(currentIndex)
        if(question!=null){
            Box(modifier = Modifier.fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1B5E20),
                            Color(0xFF4CAF50)
                        )
                    )
                )){

                QuestionContent(question = question,
                    questionNumber = currentIndex + 1,
                    totalQuestions = questions.size,
                    onBookmarkClick = {
                        viewModel.toggleBookmark(question)
                    }, onNextClick = {
                        if (currentIndex < questions.size - 1) {
                            currentIndex++
                        }
                        else {
                            val finalScore = viewModel.currentQuizScore
                            viewModel.updateQuizStats(
                                totalQuestions = questions.size,score = finalScore)
                            viewModel.loadScore()
                            viewModel.loadAttemptedCount()
                            viewModel.loadStats()
                            viewModel.loadCategoryStats()

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
    else{
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            Text(text = "No Questions Found")

        }
        return
    }

}

