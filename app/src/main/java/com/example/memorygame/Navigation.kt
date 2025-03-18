package com.example.memorygame

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import com.example.memorygame.Screens.MainScreen
import com.example.memorygame.Screens.PlayScreen
import com.example.memorygame.Screens.SettingsScreen
import com.example.memorygame.Screens.StatsScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable("play") {
            PlayScreen(onNewGameClick = {},
                onFabClick = {})
        }
        composable("settings") {
            SettingsScreen()
        }
        composable("stats") {
            StatsScreen()
        }
    }
}