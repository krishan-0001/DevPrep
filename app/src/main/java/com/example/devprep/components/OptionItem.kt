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
               index: Int,
               selectedOption: Int,
               onClick: () -> Unit){
    Card(modifier = Modifier.fillMaxWidth()
        .padding(vertical = 6.dp)
        .clickable{onClick()},
        colors = CardDefaults.cardColors(
            containerColor = if(selectedOption==index){
                Color(0xFFD6EAF8)
            }
            else{
                Color.White
            }
        )) {
        Text(text = text, modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge)
    }

    }

