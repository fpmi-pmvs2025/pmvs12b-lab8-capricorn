package com.example.memorygame.presentation.ui


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.memorygame.presentation.ui.screens.ConfigurationScreen
import com.example.memorygame.presentation.ui.screens.MainScreen
import com.example.memorygame.presentation.ui.screens.PlayScreen
import com.example.memorygame.presentation.ui.screens.SettingsScreen
import com.example.memorygame.presentation.ui.screens.StatsScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main" // Начинаем с MainScreen
    ) {
        // Главный экран
        composable("main") {
            MainScreen(
                onPlayClick = {
                    // Переход на экран конфигураций
                    navController.navigate("configuration")
                },
                onSettingsClick = {
                    // Переход на экран настроек
                    navController.navigate("settings")
                },
                onStatsClick = {
                    // Переход на экран статистики
                    navController.navigate("stats")
                }
            )
        }

        // Экран конфигураций
        composable("configuration") {
            ConfigurationScreen(
                onConfigurationSelected = { numberOfCards ->
                    // Переход на PlayScreen с аргументом
                    navController.navigate("play/$numberOfCards")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Экран игры
        composable(
            route = "play/{numberOfCards}", // Маршрут с аргументом
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

        // Экран настроек
        composable("settings") {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }

        // Экран статистики
        composable("stats") {
            StatsScreen(onBackClick = { navController.popBackStack() })
        }
    }
}