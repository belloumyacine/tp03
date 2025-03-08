package com.example.imatah.presentation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    // ...existing code...

    Button(onClick = { navController.navigate("add_report") }) {
        Text("Add damaged road")
    }

    // ...existing code...
}
