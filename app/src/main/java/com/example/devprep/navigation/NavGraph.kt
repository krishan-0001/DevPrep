package com.example.devprep.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.devprep.data.local.QuestionViewModel
import com.example.devprep.screens.BookMarkScreen
import com.example.devprep.screens.HomeScreen
import com.example.devprep.screens.ProfileScreen
import com.example.devprep.screens.ProgressScreen
import com.example.devprep.screens.QuestionScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier){
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ){
        composable(Routes.HOME){
            HomeScreen(navController)
        }
        composable(Routes.BOOKMARK){
            BookMarkScreen()
        }
        composable(Routes.PROGRESS){
            ProgressScreen()
        }
        composable(Routes.PROFILE){
            ProfileScreen()
        }
        composable("${Routes.QUESTIONS}/{category}") { backStackEntry->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            QuestionScreen(category = category, navController = navController)

        }
    }
    }



