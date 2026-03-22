package com.krish.devprep.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@Composable
fun SignUpScreen(navController: NavHostController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
//    var imageUrl by remember {
//        mutableStateOf<Uri?>(null)
//    }
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri ->
//        imageUrl = uri
//
//    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2E7D32),
                        Color(0xFF66BB6A)
                    )
                )
            )
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = com.krish.devprep.R.drawable.login_icon),
                contentDescription = "Signup Image",
                modifier = Modifier.size(160.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Create Account 🚀",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ){
                Column(modifier = Modifier.padding(20.dp)){
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = "Person")
                        },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = "Email Icon")
                        },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        leadingIcon = {
                            Icon(Icons.Default.Password, contentDescription = "Email Icon")
                        },
                        trailingIcon = {
                            IconButton(onClick = {passwordVisible = !passwordVisible}) {
                                if(passwordVisible){
                                    Icon(Icons.Default.Visibility, contentDescription = "Visible")
                                }
                                else{
                                    Icon(Icons.Default.VisibilityOff, contentDescription = "Invisible")
                                }
                            }
                        },
                        label = { Text("Password") },
                        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        leadingIcon = {
                            Icon(Icons.Default.Password, contentDescription = "Email Icon")
                        },
                        trailingIcon = {
                            IconButton(onClick = {passwordVisible = !passwordVisible}) {
                                if(passwordVisible){
                                    Icon(Icons.Default.Visibility, contentDescription = "Visible")
                                }
                                else{
                                    Icon(Icons.Default.VisibilityOff, contentDescription = "Invisible")
                                }
                            }
                        },
                        label = { Text("Confirm Password") },
                        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = {
                        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                            Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if(password!=confirmPassword){
                            Toast.makeText(context,"Passwords do not match",Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener { result ->
                                val user = result.user
                                val uid = user?.uid ?: return@addOnSuccessListener

                                            val userMap = hashMapOf(
                                                "uid" to uid,
                                                "name" to name,
                                                "email" to email,
                                                "score" to 0,
                                                "quizzesAttempted" to 0,
                                                "totalQuestions" to 0,
                                                "createdAt" to FieldValue.serverTimestamp(),
                                            )
                                            db.collection("users")
                                                .document(uid)
                                                .set(userMap)
                                                .addOnSuccessListener {
                                                    Toast.makeText(context,"Account created successfully",Toast.LENGTH_SHORT).show()
                                                    Log.d("Firebase", "User created successfully")
                                                    navController.navigate("home") {
                                                        popUpTo("signup") { inclusive = true }
                                                    }
                                                }




                            }
                            .addOnFailureListener {
                                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                            }

                    },modifier = Modifier.fillMaxWidth()
                        .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Sign Up")
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Already have an account? Login",
                        modifier = Modifier.clickable {
                            navController.navigate("login")
                        },
                        color = Color.Gray
                    )
                }
            }

        }
    }

}
