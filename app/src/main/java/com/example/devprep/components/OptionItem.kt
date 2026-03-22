package com.example.devprep.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OptionItem(text: String,
               optionNumber: Int,
               selectedOption: Int,
               correctAnswer: Int,
               showResult: Boolean,
               onClick: () -> Unit){

    val backgroundColor = when{
        showResult && optionNumber==correctAnswer ->
            Color(0xFF2E7D32)
        showResult && optionNumber==selectedOption && selectedOption != correctAnswer ->
            Color(0xFFC62828)
        !showResult && selectedOption==optionNumber ->
            Color(0xFF4CAF50)

        else->
            Color(0xFF263238)
    }


    Card(modifier = Modifier.fillMaxWidth()
        .padding(vertical = 6.dp)
        .clickable(enabled = !showResult){onClick()},
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Text(text = text, modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White)
    }

    }

