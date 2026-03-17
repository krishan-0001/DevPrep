package com.example.devprep.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@Composable
fun SignUpScreen(navController: NavHostController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var imageUrl by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUrl = uri

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(30.dp))
        Box(
            modifier = Modifier.size(100.dp)
                .background(Color.Gray, shape = CircleShape)
                .clickable {
                    launcher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = "Add Photo",
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->
                            val user = result.user
                            val uid = user?.uid ?: return@addOnSuccessListener
                            if (imageUrl != null) {
                                val storageRef = FirebaseStorage.getInstance().reference
                                    .child("profile_images/$uid.jpg")
                                storageRef.putFile(imageUrl!!)
                                    .continueWithTask {
                                        storageRef.downloadUrl
                                    }
                                    .addOnSuccessListener { downloadUrl ->
                                        val userMap = hashMapOf(
                                            "uid" to uid,
                                            "name" to name,
                                            "email" to email,
                                            "score" to 0,
                                            "quizzesAttempted" to 0,
                                            "totalQuestions" to 0,
                                            "createdAt" to FieldValue.serverTimestamp(),
                                            "profileImage" to downloadUrl.toString()
                                        )
                                        db.collection("users")
                                            .document(uid)
                                            .set(userMap)
                                            .addOnSuccessListener {
                                                navController.navigate("home") {
                                                    popUpTo("signup") { inclusive = true }
                                                }
                                            }

                                    }

                            } else {
                                val userMap = hashMapOf(
                                    "uid" to uid,
                                    "name" to name,
                                    "email" to user.email,
                                    "profileImage" to "",
                                    "score" to 0,
                                    "quizzesAttempted" to 0,
                                    "totalQuestions" to 0,
                                    "createdAt" to FieldValue.serverTimestamp()
                                )
                                db.collection("users")
                                    .document(uid)
                                    .set(userMap)
                                    .addOnSuccessListener {
                                        navController.navigate("home") {
                                            popUpTo("signup") { inclusive = true }
                                        }
                                    }
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firebase", "Auth Error: ${e.message}")
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        Spacer(Modifier.height(16.dp))

        TextButton(
            onClick = {
                navController.navigate("login")
            }
        ) {
            Text("Already have an account? Login")
        }
    }
}
