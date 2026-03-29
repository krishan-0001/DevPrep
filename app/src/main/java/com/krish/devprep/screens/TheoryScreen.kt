package com.krish.devprep.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.room.util.TableInfo
import com.krish.devprep.data.viewmodel.TheoryViewModel

@Composable
fun TheoryScreen(category: String,viewModel: TheoryViewModel){

    LaunchedEffect(category) {
        viewModel.loadTheory(category)
    }
    val theoryList = viewModel.theoryList
    var expandedId by remember{
        mutableStateOf<Int?>(null)
    }
    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp)) {
        Text(
            text = "$category Theory 📚",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn{
            items(theoryList){theory->
                val isExpanded = theory.id == expandedId

                Card(modifier = Modifier.fillMaxWidth()
                    .padding(6.dp)
                    .animateContentSize()
                    .clickable{
                        expandedId = if(isExpanded) null else theory.id
                    },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp) )
                    {

                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = theory.title,
                                fontWeight = FontWeight.Bold)
                            Text(
                                text = if (isExpanded) "▲" else "▼"
                            )
                            if(isExpanded){
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = theory.content,
                                    fontSize = 14.sp)
                            }
                        }
                    }
                }

            }

        }
    }
}