package com.krish.devprep.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.krish.devprep.data.local.GuideEntity
import kotlin.math.exp

@Composable
fun GuideItem(guide: GuideEntity){

    var expanded by remember{
        mutableStateOf(false)
    }
    Card(modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){


                Column() {
                    Text(text = guide.title)

                    Text(text = guide.level,
                        color = Color.Gray)
                }

                IconButton(onClick = {expanded = !expanded}) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = "Expanded"
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))

            Text(text = guide.description)
            if(expanded){

                Spacer(modifier = Modifier.height(6.dp))

                Text(text = "What to learn:",
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                Text(text = guide.content)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Resources:",
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                guide.resources.forEach {
                    Text(
                        text = "• $it",
                        color = Color(0xFF1B5E20)
                    )
                }

            }
        }
    }
}