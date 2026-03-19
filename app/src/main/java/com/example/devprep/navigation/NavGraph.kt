package com.example.devprep.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.devprep.data.local.AppDatabase
import com.example.devprep.data.local.QuestionViewModel
import com.example.devprep.data.local.QuestionViewModelFactory
import com.example.devprep.screens.BookMarkScreen
import com.example.devprep.screens.HomeScreen
import com.example.devprep.screens.LeaderBoardScreen
import com.example.devprep.screens.LoginScreen
import com.example.devprep.screens.ProfileScreen
import com.example.devprep.screens.ProgressScreen
import com.example.devprep.screens.QuestionScreen
import com.example.devprep.screens.ResultScreen
import com.example.devprep.screens.SignUpScreen
import com.example.devprep.screens.SplashScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import java.net.URLDecoder

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier){
    val context  = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val viewModel: QuestionViewModel = viewModel(
        factory = QuestionViewModelFactory(
            database.questionDao(),
            database.quizStatsDao(),
            database.categoryStatsDao(),
            context
        )
    )
//    val auth = FirebaseAuth.getInstance()
//    val startDestination = if(auth.currentUser!=null){
//        Routes.HOME
//    }
//    else{
//        Routes.LOGIN
//    }
    val startDestination = Routes.SPLASH
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable(Routes.HOME){
            HomeScreen(navController)
        }
        composable(Routes.BOOKMARK){
            BookMarkScreen(viewModel)
        }
        composable(Routes.PROGRESS){
            ProgressScreen(navController, viewModel)
        }
        composable(Routes.PROFILE){
            ProfileScreen(navController)
        }
        composable("${Routes.QUESTIONS}/{category}") { backStackEntry->
            val categoryArg = backStackEntry.arguments?.getString("category") ?: ""
            val category = URLDecoder.decode(categoryArg, "UTF-8")
            QuestionScreen(category = category, navController = navController,viewModel)

        }
        composable("results/{score}/{total}") {backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?:0
            val total = backStackEntry.arguments?.getString("total")?.toInt() ?:0
            ResultScreen(score,total,navController,viewModel)

        }
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.SIGNUP) {
            SignUpScreen(navController)
        }
        composable(Routes.LEADERBOARD) {
            LeaderBoardScreen()
        }
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
    }
}
