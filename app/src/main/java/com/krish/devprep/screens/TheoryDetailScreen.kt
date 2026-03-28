package com.krish.devprep.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.krish.devprep.data.Theory
import com.krish.devprep.data.viewmodel.TheoryViewModel

@Composable
fun TheoryDetailScreen(id: Int, viewModel: TheoryViewModel){

    var theory by remember {
        mutableStateOf<Theory?>(null)
    }
    LaunchedEffect(Unit) {
        theory = viewModel.getTheoryById(id)
    }
    theory?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = it.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = it.content)

        }
    }
}