package com.example.memorygame.presentation.ui


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.memorygame.presentation.ui.screens.ConfigurationScreen
import com.example.memorygame.presentation.ui.screens.MainScreen
import com.example.memorygame.presentation.ui.screens.SettingsScreen
import com.example.memorygame.screens.PlayScreen
import com.example.memorygame.screens.StatsScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                onPlayClick = {
                    navController.navigate("configuration")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onStatsClick = {
                    navController.navigate("stats")
                }
            )
        }

        composable("configuration") {
            ConfigurationScreen(
                onConfigurationSelected = { numberOfCards ->
                    navController.navigate("play/$numberOfCards")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = "play/{numberOfCards}",
            arguments = listOf(navArgument("numberOfCards") { type = NavType.IntType })
        ) { backStackEntry ->
            val numberOfCards = backStackEntry.arguments?.getInt("numberOfCards") ?: 4
            PlayScreen(
                numberOfCards = numberOfCards,
                onNewGameClick = {
                    navController.navigate("configuration")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("settings") {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }

        composable("stats") {
            StatsScreen(onBackClick = { navController.popBackStack() })
        }
    }
}