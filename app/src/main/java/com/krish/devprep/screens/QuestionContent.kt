package com.krish.devprep.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krish.devprep.R
import com.krish.devprep.components.OptionItem
import com.krish.devprep.data.local.QuestionEntity
import kotlinx.coroutines.delay

@Composable
fun QuestionContent(question: QuestionEntity,
                   questionNumber: Int,
                   totalQuestions: Int,
                   onBookmarkClick: () -> Unit,
                   onNextClick: () -> Unit,
                    onCheckAnswer: (Int, QuestionEntity) -> Unit){
    var selectedOption by remember(question.id) {
        mutableStateOf(-1)
    }
    var showResult by remember(question.id) {
        mutableStateOf(false)
    }
    var timeLeft by remember(question.id){
        mutableStateOf(30)
    }
    val soundPool = remember{
        android.media.SoundPool.Builder()
            .setMaxStreams(1)
            .build()
    }
    var soundId by remember{
        mutableStateOf(0)
    }
    var streamId by remember{
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "timer")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Transparent,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blink"
    )
    // Set ID of soundPool
    LaunchedEffect(Unit) {
        soundId = soundPool.load(context,R.raw.ticking_sound,1)
    }
    LaunchedEffect(question.id) {

        if(!showResult){
            timeLeft = 30
            while(timeLeft>0 && !showResult){
                if (showResult) break
                if(timeLeft in 1..5){
                   streamId = soundPool.play(soundId,1f,1f,0,0,1f)
                }
                delay(1000)
                timeLeft--
            }
            if(timeLeft==0 && !showResult){
                showResult = true
                onCheckAnswer(-1,question)
            }
        }

    }
    // Auto Next After 2 sec
    LaunchedEffect(showResult) {
        if (showResult && timeLeft == 0) {
            soundPool.stop(streamId)
            delay(2000)
            onNextClick()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            soundPool.release()
        }
    }
    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Text(text = "Question $questionNumber / $totalQuestions",
                style = MaterialTheme.typography.titleMedium, fontSize = 22.sp,
                color = Color.White)
            Text(text = "Time Left: $timeLeft sec",
                fontSize = 18.sp,
                color = if(timeLeft in 1..5 && !showResult) animatedColor else Color.White,
                modifier = Modifier.padding(vertical = 8.dp))

            IconButton(onClick = onBookmarkClick, modifier = Modifier.size(70.dp)) {
                Icon(
                    imageVector = if(question.isBookmarked){
                        Icons.Default.Bookmark
                    }else{
                        Icons.Default.BookmarkBorder
                    },
                    contentDescription = "Bookmark",
                    tint =  Color(0xFF4CAF50)
                )
                    }
            }
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = questionNumber.toFloat() / totalQuestions.toFloat(),
            modifier = Modifier.fillMaxWidth()
                .height(8.dp),
            color = Color(0xFF4CAF50)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Timer
        LinearProgressIndicator(
            progress = timeLeft/30f,
            modifier = Modifier.fillMaxWidth()
                .height(6.dp),
            color = if(timeLeft in 1..5 && !showResult) Color.Red else Color(0xFF4CAF50)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = question.question,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(6.dp))
                OptionItem("A. ${question.option1}", 1, selectedOption=selectedOption, correctAnswer = question.correctAnswer,showResult=showResult) { selectedOption = 1 }
                OptionItem("B. ${question.option2}", 2, selectedOption=selectedOption, correctAnswer = question.correctAnswer,showResult=showResult) { selectedOption = 2 }
                OptionItem("C. ${question.option3}", 3, selectedOption=selectedOption, correctAnswer = question.correctAnswer,showResult=showResult) { selectedOption = 3 }
                OptionItem("D. ${question.option4}", 4, selectedOption=selectedOption, correctAnswer = question.correctAnswer,showResult=showResult) { selectedOption = 4 }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        showResult = true
                        onCheckAnswer(selectedOption, question)

                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedOption != -1
                ) {
                    Text(text = "Check Answer")
                }
                if(showResult){
                    Spacer(modifier = Modifier.height(16.dp))
                    if (timeLeft == 0 && selectedOption == -1) {
                        Text("Time's Up ⏰", color = Color.Red, fontSize = 20.sp)
                    }
                    if(selectedOption==question.correctAnswer){
                        Text("Correct Answer ✅",  color = Color(0xFF81C784))
                    }
                    else{
                        Text("Wrong Answer ❌", color = Color(0xFFEF5350))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = question.explanation,
                        color = Color.Black,
                        fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = onNextClick,
                        enabled = showResult,
                        modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Next Question")
                    }
                }
            }

        }

        }
    }
