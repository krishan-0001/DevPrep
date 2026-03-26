package com.krish.devprep.navigation

import android.R.attr.category
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.krish.devprep.data.database.AppDatabase
import com.krish.devprep.data.database.DatabaseProvider
import com.krish.devprep.data.viewmodel.GuideViewModel
import com.krish.devprep.data.viewmodel.QuestionViewModel
import com.krish.devprep.data.viewmodel.AppViewModelFactory
import com.krish.devprep.data.viewmodel.CodingViewModel
import com.krish.devprep.screens.BookMarkScreen
import com.krish.devprep.screens.CodingScreen
import com.krish.devprep.screens.GuideScreen
import com.krish.devprep.screens.HomeScreen
import com.krish.devprep.screens.LeaderBoardScreen
import com.krish.devprep.screens.LoginScreen
import com.krish.devprep.screens.ProfileScreen
import com.krish.devprep.screens.ProgressScreen
import com.krish.devprep.screens.QuestionScreen
import com.krish.devprep.screens.ResultScreen
import com.krish.devprep.screens.SignUpScreen
import com.krish.devprep.screens.SplashScreen
import java.net.URLDecoder

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier){
    val context  = LocalContext.current
    val db = DatabaseProvider.provideDatabase(context)

    val factory = AppViewModelFactory(
        questionDao = db.questionDao(),
        guideDao = db.guideDao(),
        codingDao = db.codingDao(),
        quizStatsDao = db.quizStatsDao(),
        categoryStatsDao = db.categoryStatsDao(),
        context = context
    )
    val questionViewModel: QuestionViewModel = viewModel(factory = factory)
    val guideViewModel: GuideViewModel = viewModel(factory = factory)
    val codingViewModel: CodingViewModel = viewModel(factory = factory)


    val startDestination = Routes.SPLASH
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable(Routes.HOME){
            HomeScreen(navController, questionViewModel)
        }
        composable(Routes.BOOKMARK){
            BookMarkScreen(questionViewModel)
        }
        composable(Routes.PROGRESS){
            ProgressScreen(navController, questionViewModel)
        }
        composable(Routes.PROFILE){
            ProfileScreen(navController)
        }
        composable("${Routes.QUESTIONS}/{category}") { backStackEntry->
            val categoryArg = backStackEntry.arguments?.getString("category") ?: ""
            val category = URLDecoder.decode(categoryArg, "UTF-8")
            QuestionScreen(category = category, navController = navController,questionViewModel)

        }
        composable("results/{score}/{total}") {backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?:0
            val total = backStackEntry.arguments?.getString("total")?.toInt() ?:0
            ResultScreen(score,total,navController,questionViewModel)

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
        composable(Routes.GUIDE) {
            GuideScreen(guideViewModel)
        }
        composable("${Routes.CODING}/{category}" ) {
            val category = it.arguments?.getString("category") ?: ""
            CodingScreen(category,codingViewModel)
        }
    }
}
