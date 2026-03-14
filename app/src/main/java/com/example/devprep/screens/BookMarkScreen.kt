package com.example.devprep.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devprep.data.local.QuestionViewModel

@Composable
fun BookMarkScreen(viewModel: QuestionViewModel){

    val bookmarkedQuestions by viewModel.bookmarkedQuestions.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp)) {
        Text(text = "BookMarks",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn() {
            items(bookmarkedQuestions){question->
                var showExplanation by remember{
                    mutableStateOf(false)
                }
                Card(modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(16.dp)) {

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = question.question,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium)

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            Button(onClick = {
                                showExplanation = !showExplanation
                            }) {
                                if(showExplanation){
                                    Text(text = "Hide Explanation")
                                }
                                else{
                                    Text(text = "Show Explanation")
                                }
                            }
                            if(showExplanation){
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = question.explanation,
                                    color = Color.Gray)
                            }
                            IconButton(onClick = {viewModel.toggleBookmark(question)}) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = "Remove Bookmark"
                                )
                            }
                        }
                    }

                }

            }
        }
    }

}