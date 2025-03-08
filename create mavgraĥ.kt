package com.example.imatah.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.imatah.presentation.AddReportScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            // HomeScreen content
        }
        composable("add_report") {
            AddReportScreen(navController = navController, onBack = { navController.popBackStack() })
        }
    }
}
