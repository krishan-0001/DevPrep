package com.krish.devprep.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController,
           isDarkMode: Boolean,
           onToggleTheme: ()-> Unit){

    val auth = FirebaseAuth.getInstance()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2E7D32),
            titleContentColor = Color.White
        ),
        title = {
            Text(text = "DevPrep",
                fontWeight = FontWeight.Bold)
        },
        actions = {
            IconButton(onClick = {
                navController.navigate("profile")
            }
            ) {
                Icon(Icons.Default.AccountCircle,contentDescription = "Profile")
            }
            IconButton(onClick = {
                auth.signOut()
                navController.navigate("login"){
                    popUpTo("home"){inclusive=true}
                }
            }) {
                Icon(Icons.Default.ExitToApp,contentDescription = "Logout")
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(6.dp)
                    .size(26.dp)
            ){
                IconButton(onClick = { onToggleTheme() }) {
                    Icon(
                        imageVector = if(isDarkMode){
                            Icons.Default.LightMode
                        }
                        else{
                            Icons.Default.DarkMode
                        },
                        contentDescription = "Toggle Theme"
                    )
                }

            }

        }
    )
}
