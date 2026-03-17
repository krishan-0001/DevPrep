package com.example.devprep.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.devprep.data.local.QuestionViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ResultScreen(
    score: Int, totalQuestions: Int, navController: NavHostController,
    viewModel: QuestionViewModel

) {

    var isUpdated by remember {
        mutableStateOf(false)
    }
    val percentage = (score.toFloat() / (totalQuestions * 10)) * 100
    LaunchedEffect(Unit) {
        if(!isUpdated){
            viewModel.updateQuizStats(totalQuestions,score)
            isUpdated = true
        }

    }
    val message = when {
        percentage >= 90 -> "Excellent 🚀"
        percentage >= 70 -> "Good Job 👍"
        percentage >= 50 -> "Keep Practicing 📚"
        else -> "Try Again 💪"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B5E20)),
        contentAlignment = Alignment.Center
    ) {

        var animatedScore by remember { mutableStateOf(0) }
        LaunchedEffect(score) {
            for (i in 0..score) {
                animatedScore = i
                delay(50)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "DevPrep",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Quiz Completed 🎉",
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Score", fontSize = 36.sp, fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Score with confetti
                    Box(modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "$animatedScore / ${totalQuestions * 10} Score",
                            fontSize = 36.sp,
                            color = Color.Yellow,
                            fontWeight = FontWeight.Bold
                        )

                        val confettiList = remember { List(30) { Confetti() } }
                        confettiList.forEach { confetti ->
                            val scale = remember { Animatable(0f) }
                            LaunchedEffect(Unit) {
                                scale.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(durationMillis = Random.nextInt(400, 800))
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .offset(x = confetti.x.dp, y = confetti.y.dp)
                                    .size(8.dp)
                                    .scale(scale.value)
                                    .background(confetti.color, CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    CircularProgressIndicator(
                        progress = percentage / 100f,
                        strokeWidth = 14.dp,
                        modifier = Modifier.size(140.dp),
                        color = Color(0xFF4CAF50)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${percentage.toInt()}% Accuracy", fontSize = 22.sp
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back to Home", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Retry Quiz", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = message,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

data class Confetti(
    val x: Int = Random.nextInt(-100, 100),
    val y: Int = Random.nextInt(-150, -50),
    val color: Color = Color(
        red = Random.nextFloat(), green = Random.nextFloat(), blue = Random.nextFloat(), alpha = 1f
    )
)
