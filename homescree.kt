package com.example.imatah.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Example of existing content
        Text("Welcome to the Imatah App")
        Spacer(modifier = Modifier.height(16.dp))

        // Button to navigate to the AddReportScreen
        Button(onClick = { navController.navigate("add_report") }) {
            Text("Add damaged road")
        }
    }
}
