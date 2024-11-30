package com.example.controlward

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.controlward.ui.AddDisasterScreen
import com.example.controlward.ui.DisasterDetailScreen
import com.example.controlward.ui.TabScreen

@Composable
fun ScreenNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "TabScreen") {
        composable("TabScreen") {
            TabScreen(navController)
        }
        composable("AddDisasterScreen") {
            AddDisasterScreen(navController)
        }
        composable(
            route = "DisasterDetailScreen/{image}/{text}",
            arguments = listOf(
                navArgument("image") { type = NavType.StringType },
                navArgument("text") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val image = backStackEntry.arguments?.getString("image").toString()
            val text = backStackEntry.arguments?.getString("text").toString()

            DisasterDetailScreen(image, text)
        }
    }
}