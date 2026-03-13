package com.example.devprep.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.devprep.data.BottomNavItem
import com.example.devprep.navigation.Routes

@Composable
fun BottomBar(navController: NavHostController){
    val items = listOf(
        BottomNavItem("HOME", Icons.Default.Home, Routes.HOME),
        BottomNavItem("BOOKMARK",Icons.Default.Bookmark,Routes.BOOKMARK),
        BottomNavItem("PROGRESS",Icons.Default.BarChart,Routes.PROGRESS),
        BottomNavItem("PROFILE",Icons.Default.Person,Routes.PROFILE)
    )
    NavigationBar() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach{item->
            NavigationBarItem(selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(Routes.HOME)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name
                    )
                },
                label = {
                    Text(text = item.name)
                }
            )

        }
    }
}
