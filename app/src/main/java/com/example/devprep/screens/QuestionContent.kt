package com.example.devprep.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devprep.components.OptionItem
import com.example.devprep.data.local.QuestionEntity

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
    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Question $questionNumber / $totalQuestions",
                style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
            IconButton(onClick = onBookmarkClick, modifier = Modifier.size(70.dp)) {
                Icon(
                    imageVector = if(question.isBookmarked){
                        Icons.Default.Bookmark
                    }else{
                        Icons.Default.BookmarkBorder
                    },
                    contentDescription = "Bookmark")
                    }
            }
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = questionNumber.toFloat() / totalQuestions.toFloat(),
            modifier = Modifier.fillMaxWidth()
                .height(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Text(
                text = question.question,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(6.dp))

            OptionItem("A. ${question.option1}", 1, selectedOption=selectedOption, correctAnswer = question.correctAnswer,showResult=showResult) { selectedOption = 1 }
            OptionItem("B. ${question.option2}", 2, selectedOption=selectedOption, correctAnswer = question.correctAnswer,showResult=showResult) { selectedOption = 2 }
            OptionItem("C. ${question.option3}", 3, selectedOption=selectedOption, correctAnswer = question.correctAnswer,showResult=showResult) { selectedOption = 3 }
            OptionItem("D. ${question.option4}", 4, selectedOption=selectedOption, correctAnswer = question.correctAnswer,showResult=showResult) { selectedOption = 4 }
            Spacer(modifier = Modifier.height(20.dp))
           Button(
                onClick = {
                    onCheckAnswer(selectedOption, question)
                    showResult = true
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedOption != -1
            ) {
                Text(text = "Check Answer")
            }
            if(showResult){
                Spacer(modifier = Modifier.height(16.dp))
                if(selectedOption==question.correctAnswer){
                    Text("Correct Answer ✅", color = Color(0xFF2E7D32))
                }
                else{
                    Text("Wrong Answer ❌", color = Color.Red)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = question.explanation)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = onNextClick,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Next Questions")
                }
            }
        }
        }
    }

