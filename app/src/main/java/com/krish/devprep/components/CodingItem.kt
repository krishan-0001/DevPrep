package com.krish.devprep.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.krish.devprep.data.local.CodingQuestionEntity
import com.krish.devprep.data.viewmodel.CodingViewModel

@Composable
fun CodingItem(question: CodingQuestionEntity, viewModel: CodingViewModel){

    var showCode by remember{
        mutableStateOf(false)
    }
    var showExplanation by remember {
        mutableStateOf(false)
    }
    var clipBoardManager = LocalClipboardManager.current

    Card(modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = question.question,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(6.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){

                Text(text = "Level: ${question.difficulty}")
                Text(text = "Company: ${question.company}")
            }
            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                Button(onClick = {showCode = !showCode}){
                    Text(text = if(showCode) "Hide Code" else "Show Code")
                }
                Button(onClick = {showExplanation = !showExplanation}) {
                    Text(text = if(showExplanation) "Hide Explanation" else "Show Explanation")
                }
            }

            if(showCode){
                Spacer(modifier = Modifier.height(10.dp))

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF263238))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {

                        Text(text = "Copy code",
                            color = Color.White)

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = {
                            clipBoardManager.setText(AnnotatedString(question.code))
                        }) {
                            Text("Copy Code 📋")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = question.code,
                            color = Color.White,
                            modifier = Modifier.padding(10.dp))
                    }

                }
            }
            if(showExplanation){
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = question.explanation,
                    color = Color.DarkGray)

            }
            Spacer(modifier = Modifier.height(10.dp))

            // Bookmark
            TextButton(onClick = {
                viewModel.toggleBookmark(question)
            }) {
                Text(text = if(question.isBookmarked) "★ Bookmarked" else "☆ Bookmark")
            }

            }



            }
        }


