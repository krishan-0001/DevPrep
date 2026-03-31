package com.krish.devprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            var isDarkTheme by remember{
                mutableStateOf(false)
            }
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val showBar = currentRoute in  listOf(
                "home",
                "bookmark",
                "progress",
                "profile",
                "leaderboard",
            )

            DevPrepTheme(darkTheme = isDarkTheme) {
                Scaffold(topBar = {
                    if(showBar || currentRoute?.startsWith("category")==true){
                        TopBar(navController,
                            isDarkMode = isDarkTheme,
                            onToggleTheme = {
                                isDarkTheme = !isDarkTheme
                            })}
                    },
                    bottomBar = { if(showBar){
                    BottomBar(navController)

                } }) {paddingValues ->
                    NavGraph(navController,
                        modifier = Modifier.padding(paddingValues))

                }
            }
        }
    }
}

