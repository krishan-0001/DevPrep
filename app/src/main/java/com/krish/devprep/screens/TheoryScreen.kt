package com.krish.devprep.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.room.util.TableInfo
import com.krish.devprep.data.viewmodel.TheoryViewModel

@Composable
fun TheoryScreen(navController: NavHostController,category: String,viewModel: TheoryViewModel){

    LaunchedEffect(Unit) {
        viewModel.loadTheory(category)
    }
    val theoryList = viewModel.theoryList

    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp)) {
        Text(
            text = "$category Theory",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn{
            items(theoryList){theory->
                Card(modifier = Modifier.fillMaxWidth()
                    .padding(6.dp),
                    shape = RoundedCornerShape(12.dp)) {

                    Column(modifier = Modifier.padding(16.dp)
                        .clickable{
                            val encoded = java.net.URLEncoder.encode(theory.content, "UTF-8")
                            navController.navigate("theoryDetail/$encoded")

                        }) {

                        Text(text = theory.title,
                            fontWeight = FontWeight.Bold)
                    }
                }

            }

        }
    }
}