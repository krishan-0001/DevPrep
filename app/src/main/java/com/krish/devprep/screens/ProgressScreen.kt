package com.krish.devprep.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.krish.devprep.data.local.QuestionViewModel

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


    Box(modifier = Modifier.fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF1B5E20),
                    Color(0xFF4CAF50)
                )
            )
        )){

        Column(modifier = Modifier.fillMaxSize()
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween)
        {

            Text(text = "Your Score",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)

            AnimatedProgressRing(percentage)

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){

                StatCard("Quizzes",stats?.quizzesAttempted.toString() ?: "0")
                StatCard("Questions",stats?.totalQuestions.toString() ?: "0")
                StatCard("Score",stats?.totalScore.toString() ?: "0")

            }

            Text(text = "Category Performance",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)

            Column(modifier = Modifier.fillMaxWidth()) {
                viewModel.categoryStats.forEach{stat->
                    val progress =
                        if(stat.totalQuestions==0){
                            0f
                        }
                        else{
                            stat.correctAnswers.toFloat()/stat.totalQuestions.toFloat()
                        }
                    CategoryProgress(stat.category,progress)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

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
            progress = 1f,
            strokeWidth = 14.dp,
            color = Color.Black,
            modifier = Modifier.fillMaxSize()
        )

        CircularProgressIndicator(
            progress = animated.value,
            strokeWidth = 14.dp,
            color = Color(0xFFFFC107), // foreground
            modifier = Modifier.fillMaxSize()
        )

        Text(text = "${(animated.value*100).toInt()}%",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
    }

}

@Composable
fun StatCard(title: String, value: String){

//    val icon = when (title) {
//        "Quizzes" -> Icons.Default.Assessment
//        "Questions" -> Icons.Default.HelpOutline
//        "Score" -> Icons.Default.Star
//        else -> Icons.Default.Star
//    }
    val icon = when (title) {
        "Quizzes" -> Icons.Default.Assessment
        "Questions" -> Icons.Default.HelpOutline
        "Score" -> Icons.Default.Star
        else -> Icons.Default.Star
    }

    val bgColor = when (title) {
        "Quizzes" -> Color(0xFF1565C0)   // Blue
        "Questions" -> Color(0xFFEF6C00) // Orange
        "Score" -> Color(0xFF6A1B9A)     // Purple
        else -> Color.DarkGray
    }

    val iconColor = when (title) {
        "Quizzes" -> Color(0xFF90CAF9)
        "Questions" -> Color(0xFFFFCC80)
        "Score" -> Color(0xFFCE93D8)
        else -> Color.LightGray
    }

    Card(modifier = Modifier.width(110.dp)
        .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        )
    ){

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = "Icons",
                tint = iconColor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold
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

            Text(text = title,color = Color.White)
            Text(text = "${(progress*100).toInt()}%",color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
                .height(10.dp),
            color = Color(0xFFFFC107)
        )
    }
}
