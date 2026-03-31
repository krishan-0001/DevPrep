package com.krish.devprep.screens

import android.graphics.Paint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.krish.devprep.components.getGroups
import com.krish.devprep.components.groupIcon


@Composable
fun  CategoryGroupScreen(module: String, navController: NavHostController){

    val expandedStates = remember{
        mutableStateMapOf<Int, Boolean>()
    }
    
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF1B5E20),
                    Color(0xFF4CAF50)
                )
            )
        )
        ) {

        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "📚 $module",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            val groups = getGroups(module)
            LazyColumn(contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)) {

                itemsIndexed(groups){index ,group->
                    Spacer(modifier = Modifier.height(6.dp))
                    val isExpanded = expandedStates[index] ?: false

                    Card(modifier = Modifier.fillMaxWidth()
                        .animateContentSize()
                        .border(
                            1.dp,Color.White.copy(0.15f),RoundedCornerShape(16.dp)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2E7D32)
                        ),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start) {

                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = groupIcon(group.title),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Text(text = "${index+1}. ${group.title}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White)

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(Color.White.copy(0.15f))
                                        .clickable {
                                            expandedStates[index] = !isExpanded
                                        }
                                        .padding(6.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isExpanded)
                                            Icons.Default.KeyboardArrowUp
                                        else
                                            Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                            Text(
                                text = "(${group.categories.size} Topics)",
                                fontSize = 13.sp,
                                color = Color(0xFFC8E6C9),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            if(isExpanded){
                                Spacer(modifier = Modifier.height(12.dp))

                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    group.categories.forEach { category->
                                        Card(modifier = Modifier.fillMaxWidth()
                                            .border(
                                                1.dp,Color.White.copy(0.15f),RoundedCornerShape(16.dp)
                                            ),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color.White.copy(alpha = 0.12f)
                                            )

                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .clickable {
                                                        val categoryEncoded = java.net.URLEncoder.encode(category, "UTF-8")
                                                        navController.navigate("content/$module/${categoryEncoded}")
                                                    }
                                                    .padding(14.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {

                                                Box(
                                                    modifier = Modifier
                                                        .size(8.dp)
                                                        .background(Color.White, shape = RoundedCornerShape(50))
                                                )

                                                Spacer(modifier = Modifier.width(6.dp))

                                                Text(
                                                    text = category,
                                                    color = Color.White,
                                                    fontSize = 14.sp
                                                )
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }


//                    LazyRow(contentPadding = PaddingValues(horizontal = 4.dp),
//                        horizontalArrangement = Arrangement.spacedBy(12.dp)){
//
//                        items(group.categories){ category->
//                            Card(modifier = Modifier.padding(8.dp)
//                                .width(140.dp)
//                                .clickable{
//                                    val categoryEncoded = java.net.URLEncoder.encode(category, "UTF-8")
//                                    navController.navigate("content/$module/${categoryEncoded}")
//                                },
//                                shape = RoundedCornerShape(12.dp),
//                                colors = CardDefaults.cardColors(
//                                    containerColor = Color(0xFFE0E0E0).copy(alpha = 0.15f)
//                                ),
//                                elevation = CardDefaults.cardElevation(4.dp)
//                            ) {
//                                Box(modifier = Modifier
//                                    .padding(vertical = 20.dp, horizontal = 12.dp),
//                                    contentAlignment = Alignment.Center
//                                ){
//                                    Text(text = category,
//                                        fontSize = 16.sp,
//                                        fontWeight = FontWeight.Medium,
//                                        color = Color.White,
//                                        maxLines = 2
//                                    )
//                                }
//                            }
//
//                        }
//                    }
                   // Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }

    }

}
