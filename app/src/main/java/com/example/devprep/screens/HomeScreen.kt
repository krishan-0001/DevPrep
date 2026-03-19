package com.example.devprep.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.devprep.components.CategoryCard
import com.example.devprep.components.SectionTitle
import com.example.devprep.components.appBackground
import com.example.devprep.data.Category
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeHeader(){
    Box(modifier = Modifier.fillMaxWidth()
        .height(180.dp)
        .padding(bottom =12.dp)
        .clip(
            RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
        )
        .background(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF2E7D32),
                    Color(0xFF66BB6A))
            ))
       // .appBackground()
        ,
        contentAlignment = Alignment.TopCenter
    ){
        Column(modifier = Modifier
            .padding(top = 30.dp,start = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(text = "DevPrep", fontSize = 38.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold)
         //   SectionTitle("DevPrep")
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Android Interview Master",
                fontSize = 20.sp,
                color = Color.White.copy(0.9f))
          //  SectionTitle("Android Interview Master")
        }
    }
}
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(navController: NavHostController){
    val user = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    var name by remember {
        mutableStateOf("User")
    }
    LaunchedEffect(Unit) {
        val uid = user.currentUser?.uid
        uid?.let {
            db.collection("users").document(uid)
                .get()
                .addOnSuccessListener { doc->
                    name = doc.getString("name") ?: "User"
                }
        }
    }
    val categories = listOf(
        Category("Kotlin",Icons.Default.Code),
        Category("JetPack Compose",Icons.Default.Android),
        Category("MVVM & Architecture",Icons.Default.AccountTree),
        Category("Coroutines",Icons.Default.Sync),
        Category("Retrofit_API",Icons.Default.Cloud),
        Category("Room Database",Icons.Default.Storage),
        Category("Android Basics",Icons.Default.PhoneAndroid),
        Category("System Design",Icons.Default.Settings)
    )
    Column(modifier = Modifier.padding(4.dp, bottom = 8.dp)
      //  .padding(WindowInsets.safeDrawing.asPaddingValues())
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        HomeHeader()
        Column(modifier = Modifier.padding(16.dp,bottom = 16.dp)) {
            Text(text = "Hi $name, Ready to Ace Your Interviews?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold)
          //  SectionTitle("Hi $name, Ready to Ace Your Interviews?")
            Spacer(modifier = Modifier.height(14.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories){category->
                    CategoryCard(category,onClick = {
                        val encodedCategory = java.net.URLEncoder.encode(category.title, "UTF-8")
                        navController.navigate("questions/${encodedCategory}")
                    })

                }
            }
        }
    }



}