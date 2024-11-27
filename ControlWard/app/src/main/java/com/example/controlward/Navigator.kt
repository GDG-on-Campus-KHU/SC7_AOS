package com.example.controlward

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controlward.ui.AddDisasterScreen
import com.example.controlward.ui.TabScreen

@Composable
fun ScreenNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "TabScreen") {
        composable("TabScreen") {
            TabScreen(navController)
        }
        composable("AddDisasterScreen") {
            AddDisasterScreen()
        }
    }
}