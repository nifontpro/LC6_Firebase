package ru.nifontbus.lc6_firebase.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigate() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.MAIN
    ) {
        composable(Route.MAIN) {

        }

        composable(Route.ADD_BIO) {

        }
    }
}