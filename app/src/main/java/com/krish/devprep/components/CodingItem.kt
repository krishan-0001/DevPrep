package com.krish.devprep.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val context = LocalContext.current


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

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF263238)
                )
                ) {
                    Box(modifier = Modifier.padding(10.dp)) {
//                        Text(text = "Code",
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))

                        val clipBoardManager = LocalClipboardManager.current
                        IconButton(onClick = {
                            clipBoardManager.setText(AnnotatedString(question.code))
                            Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
                        },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {

                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy Code",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = question.code,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp,
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
                Toast.makeText(context, "Bookmark Updated", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = if(question.isBookmarked) "★ Bookmarked" else "☆ Bookmark")
            }

            }



            }
        }


