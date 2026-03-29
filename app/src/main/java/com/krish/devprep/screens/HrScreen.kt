package com.krish.devprep.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.krish.devprep.data.viewmodel.HrViewModel

@Composable
fun HrScreen(category: String, viewModel: HrViewModel){

    LaunchedEffect(category) {
        viewModel.loadQuestions(category)
    }

    val questions by viewModel.questions.collectAsState()

    LazyColumn() {

        items(questions){item->

            Card(modifier = Modifier.fillMaxWidth()
                .padding(8.dp)){
                Column(modifier = Modifier.padding(12.dp)) {

                    Text(text = item.question,
                        fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = item.answer)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "💡 Tips: ${item.tips}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "⚠ Mistakes: ${item.mistakes}")
                }

            }

        }
    }
}