package com.krish.devprep.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.krish.devprep.components.CodingItem
import com.krish.devprep.data.viewmodel.CodingViewModel

@Composable
fun CodingScreen(category: String, viewModel: CodingViewModel){


    LaunchedEffect(Unit) {
        viewModel.loadQuestions(category)
    }
    val questions = viewModel.questions

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1B5E20),
                        Color(0xFF4CAF50)
                    )
                )
            )
    ){

        Column(modifier = Modifier.fillMaxSize()
            .padding(16.dp)) {

            Text(
                text = "Coding Practice 💻",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(text = "Solve Real Interview Questions",
                color = Color.White.copy(0.9f))

            Spacer(modifier = Modifier.height(16.dp))

            if(questions.isEmpty()){
                Text(text = "No Question Found",
                    color = Color.White)
            }
            else{

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(questions) { question ->
                        CodingItem(question, viewModel)
                    }
                }
            }


        }
    }
}