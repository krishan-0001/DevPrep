package com.example.devprep.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.devprep.data.local.AppDatabase.Companion.clearDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen(navController: NavHostController){

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var score by remember {
        mutableStateOf(0L)
    }
    var quizzes by remember {
        mutableStateOf(0L)
    }
    var totalQuestions by remember {
        mutableStateOf(0L)
    }
    var profileImageUrl by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        val uid = auth.currentUser?.uid
        uid?.let {
            db.collection("users")
                .document(it)
                .get()
                .addOnSuccessListener { doc->
                    name = doc.getString("name") ?: ""
                    email = doc.getString("email") ?: ""
                    score = doc.getLong("score") ?: 0
                    quizzes = doc.getLong("quizzesAttempted") ?: 0
                    totalQuestions = doc.getLong("totalQuestions") ?: 0
                    profileImageUrl = doc.getString("profileImage") ?: ""

                }
        }
    }
    val accuracy = if(totalQuestions>0){
        ((score.toFloat()/totalQuestions.toFloat())*100).toInt()
    }
    else{
        0
    }

    Column(modifier = Modifier.fillMaxSize()
        .background(Color(0xFF0D47A1))
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        AsyncImage(
            model = if(profileImageUrl.isNotEmpty()) profileImageUrl else null,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Name: $name", fontSize = 18.sp,
            color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Email: $email", fontSize = 18.sp,
            color = Color.White)

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
            colors = CardDefaults.cardColors(Color.White)){
            Column(modifier = Modifier.padding(16.dp)) {

                Text(text = "Your Stats", fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Total Score: $score", fontSize = 18.sp)
             //   Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Quizzes Attempted: $quizzes", fontSize = 18.sp)
               // Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Total Questions: $totalQuestions", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Accuracy: $accuracy%", fontSize = 18.sp)
                LinearProgressIndicator(
                    progress = accuracy/100f,
                    modifier = Modifier.fillMaxWidth()
                        .height(8.dp))


            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                clearDatabase(context)
                withContext(Dispatchers.Main){
                    auth.signOut()
                    navController.navigate("login"){
                        popUpTo(0){inclusive = true}
                    }
                }
            }

        },modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = "Logout",
                fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))

        TextButton(onClick = {
            navController.navigate("leaderboard")
        }) {
            Text(text = "Leaderboard",
                fontSize = 28.sp,
                color = Color.White)
        }
    }


}