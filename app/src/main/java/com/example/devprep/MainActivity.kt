package com.example.devprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.devprep.components.BottomBar
import com.example.devprep.components.TopBar
import com.example.devprep.data.local.DatabaseProvider
import com.example.devprep.data.local.QuestionRepository
import com.example.devprep.navigation.NavGraph
import com.example.devprep.navigation.Routes
import com.example.devprep.screens.HomeScreen
import com.example.devprep.ui.theme.DevPrepTheme

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = DatabaseProvider.provideDatabase(applicationContext)
        val repository = QuestionRepository(db.questionDao())

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val noBarScreens = currentRoute in  listOf(
                "home",
                "bookmark",
                "progress",
                "profile",
                "leaderboard"
            )

            DevPrepTheme {
                Scaffold(topBar = {
                    if(noBarScreens){
                        TopBar(navController)}
                    },
                    bottomBar = { if(noBarScreens){
                    BottomBar(navController)

                } }) {paddingValues ->
                    NavGraph(navController,
                        modifier = Modifier.padding(paddingValues))

                }
            }
        }
    }
}

