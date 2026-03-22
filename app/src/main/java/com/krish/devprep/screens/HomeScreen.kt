package com.krish.devprep.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.krish.devprep.components.CategoryCard
import com.krish.devprep.data.Category
import com.krish.devprep.data.local.QuestionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(navController: NavHostController, viewModel: QuestionViewModel){
    val user = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    var name by remember {
        mutableStateOf("User")
    }
    LaunchedEffect(Unit) {
        viewModel.loadScore()
        viewModel.loadAttemptedCount()
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
    Box(modifier = Modifier.fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF2E7D32),
                    Color(0xFF66BB6A)
                )
            )
        )){

        Column(modifier = Modifier.fillMaxSize()
            .padding(16.dp)) {
            Text(text = "Welcome $name 👋",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)

            Text(text = "Ready to practice today?",
                color = Color.White.copy(0.9f))
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Your Progress",
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "${viewModel.attemptedCount} Questions Solved")
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "${viewModel.score} Score")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Categories",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(2.dp)
            ) {
                items(categories){category->
                    CategoryCard(category) {
                        val encoded = java.net.URLEncoder.encode(category.title, "UTF-8")
                        navController.navigate("questions/${encoded}")
                    }

                }
            }

        }
    }

}
