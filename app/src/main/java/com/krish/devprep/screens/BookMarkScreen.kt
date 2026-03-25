package com.krish.devprep.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krish.devprep.data.local.QuestionViewModel

@Composable
fun BookMarkScreen(viewModel: QuestionViewModel){

    val bookmarkedQuestions by viewModel.bookmarkedQuestions.collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF1B5E20),
                    Color(0xFF4CAF50)
                )
            )
        )){

    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Bookmarks 📌",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        if(bookmarkedQuestions.isEmpty()){
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text( text = "No Bookmarks Yet 😕",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)
            }
        }
        else{
            LazyColumn() {
                items(bookmarkedQuestions){question->
                    var showExplanation by remember{
                        mutableStateOf(false)
                    }
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        shape = RoundedCornerShape(16.dp)) {

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = question.question,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium)

                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(250.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Button(onClick = {
                                    showExplanation = !showExplanation
                                },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF5C6BC0)
                                    )) {
                                    if(showExplanation){
                                        Text(text = "Hide")
                                    }
                                    else{
                                        Text(text = "Explain")
                                    }
                                }
                                IconButton(onClick = {viewModel.toggleBookmark(question)}) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Remove Bookmark"
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))

                            }
                            AnimatedVisibility(
                                visible = showExplanation,
                                enter = expandVertically() + fadeIn(),
                                exit = shrinkVertically() + fadeOut()
                            ) {
                                Column(){
                                    Spacer(modifier = Modifier.height(10.dp))

                                    Text(
                                        text = "Explanation:",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(15.dp))

                                    Text(text = question.explanation,
                                        color = Color.Gray,
                                        fontSize = 14.sp)
                                }

                            }

                        }

                    }

                }
            }
        }

    }

}