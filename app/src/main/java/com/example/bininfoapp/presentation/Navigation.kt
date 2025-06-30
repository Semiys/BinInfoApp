package com.example.bininfoapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bininfoapp.presentation.history_screen.HistoryScreen
import com.example.bininfoapp.presentation.main_screen.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        // Главный экран
        composable(route = Screen.MainScreen.route) {
            MainScreen(
                onNavigateToHistory = {
                    navController.navigate(Screen.HistoryScreen.route)
                }
            )
        }

        // Экран истории
        composable(route = Screen.HistoryScreen.route) {
            HistoryScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}