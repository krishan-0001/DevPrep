package com.krish.devprep.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.krish.devprep.components.getGroups


@Composable
fun  CategoryGroupScreen(module: String, navController: NavHostController){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF1B5E20),
                    Color(0xFF4CAF50)
                )
            )
        )
        ) {

        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = module,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)

            Spacer(modifier = Modifier.height(15.dp))

            val groups = getGroups(module)
            LazyColumn(modifier = Modifier.padding(8.dp)) {

                itemsIndexed(groups){index ,group->
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "${index+1}. " + group.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White)

                    LazyRow(contentPadding = PaddingValues(horizontal = 4.dp)){

                        items(group.categories){ category->
                            Card(modifier = Modifier.padding(8.dp)
                                .clickable{
                                    val categoryEncoded = java.net.URLEncoder.encode(category, "UTF-8")
                                    navController.navigate("content/$module/${categoryEncoded}")
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE0E0E0)
                                ),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Box(modifier = Modifier
                                    .padding(16.dp)
                                ){
                                    Text(text = category,
                                        modifier = Modifier
                                            .background(
                                                color = Color.White,
                                                shape = RoundedCornerShape(50)
                                            )
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }

    }

}
