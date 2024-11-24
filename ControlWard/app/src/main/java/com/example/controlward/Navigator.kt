package com.example.controlward

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controlward.ui.AddDisasterLayout
import com.example.controlward.ui.TabLayout

@Composable
fun LayoutNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "TabLayout") {
        composable("TabLayout") {
            TabLayout(navController)
        }
        composable("AddDisaster") {
            AddDisasterLayout()
        }
    }
}