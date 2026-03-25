package com.krish.devprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.krish.devprep.components.BottomBar
import com.krish.devprep.components.TopBar
import com.krish.devprep.data.database.DatabaseProvider
import com.krish.devprep.data.viewmodel.QuestionRepository
import com.krish.devprep.navigation.NavGraph
import com.krish.devprep.ui.theme.DevPrepTheme

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

