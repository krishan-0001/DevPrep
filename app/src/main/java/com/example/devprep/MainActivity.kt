package com.example.devprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.devprep.components.BottomBar
import com.example.devprep.data.local.DatabaseProvider
import com.example.devprep.data.local.QuestionRepository
import com.example.devprep.navigation.NavGraph
import com.example.devprep.screens.HomeScreen
import com.example.devprep.ui.theme.DevPrepTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val db = DatabaseProvider.provideDatabase(applicationContext)
        val repository = QuestionRepository(db.questionDao())

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            DevPrepTheme {
                val navController = rememberNavController()
                Scaffold(bottomBar = { BottomBar(navController) }) {paddingValues ->
                    NavGraph(navController,
                        modifier = Modifier.padding(paddingValues))

                }
            }
        }
    }
}

