package com.example.devprep.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.devprep.data.local.QuestionViewModel

@Composable
fun ProgressScreen(navController: NavHostController,
                   viewModel: QuestionViewModel){
    LaunchedEffect(Unit) {
        viewModel.loadStats()
        viewModel.loadCategoryStats()
    }
    val stats = viewModel.quizStats
    val percentage =
        stats?.let {
            if (it.totalQuestions == 0) 0f
            else it.totalScore.toFloat() / (it.totalQuestions * 10)
        } ?: 0f


    Column(modifier = Modifier.fillMaxSize()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {

        Text(text = "Your Score",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)

        //Spacer(modifier = Modifier.height(6.dp))

        AnimatedProgressRing(percentage)
       // Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){

            StatCard("Quizzes",stats?.quizzesAttempted.toString() ?: "0")
            StatCard("Questions",stats?.totalQuestions.toString() ?: "0")
            StatCard("Score",stats?.totalScore.toString() ?: "0")

        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Category Performance",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold)
        //Spacer(modifier = Modifier.height(16.dp))

        viewModel.categoryStats.forEach{stat->
            val progress =
                if(stat.totalQuestions==0){
                    0f
                }
            else{
                stat.correctAnswers.toFloat()/stat.totalQuestions.toFloat()
                }
            CategoryProgress(stat.category,progress)
            Spacer(modifier = Modifier.height(10.dp))
        }


    }
}
@Composable
fun AnimatedProgressRing(percentage: Float){
    val animated = remember{
        Animatable(0f)
    }
    LaunchedEffect(percentage) {
        animated.animateTo(percentage, animationSpec = tween(1200))
    }

    Box(modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.Center) {

        CircularProgressIndicator(
            progress = animated.value,
            strokeWidth = 14.dp,
            modifier = Modifier.fillMaxSize()
        )

        Text(text = "${(animated.value*100).toInt()}%",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
    }

}

@Composable
fun StatCard(title: String, value: String){

    Card(modifier = Modifier.width(110.dp)
        .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CategoryProgress(title: String, progress: Float) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = title)
            Text(text = "${(progress*100).toInt()}%")
            Spacer(modifier = Modifier.height(8.dp))

        }
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
                .height(10.dp)
        )
    }
}
