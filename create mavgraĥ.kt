package com.example.imatah.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.imatah.presentation.AddReportScreen
import com.example.imatah.presentation.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("add_report") {
            AddReportScreen(navController = navController, onBack = { navController.popBackStack() })
        }
    }
}
