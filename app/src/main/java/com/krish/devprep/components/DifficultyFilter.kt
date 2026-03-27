package com.krish.devprep.components

import android.widget.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.krish.devprep.data.viewmodel.CodingViewModel
import com.krish.devprep.ui.filter.Difficulty

@Composable
fun DifficultyFilter(viewModel: CodingViewModel){

    val selected by viewModel.selectedDifficulty.collectAsState()

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Difficulty.values().forEach { difficulty->

            Button(onClick = {viewModel.setDifficulty(difficulty)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(selected==difficulty){
                        when(difficulty){
                            Difficulty.EASY -> Color(0xFF2E7D32)
                            Difficulty.MEDIUM -> Color(0xFFF9A825)
                            Difficulty.HARD -> Color(0xFFC62828)
                            else -> Color.DarkGray
                        }
                    }
                    else{
                        Color.LightGray
                    }
                )
            ){

                Text(text = difficulty.name)
            }

        }
    }
}