package ru.nifontbus.lc6_firebase.screen.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.nifontbus.lc6_firebase.screen.add_bio.AddBioScreen
import ru.nifontbus.lc6_firebase.screen.main.MainScreen

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi

@Composable
fun Navigate() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.MAIN
    ) {
        composable(Route.MAIN) {
            MainScreen(
                onAddScreen = { navController.navigate(Route.ADD_BIO) }
            )
        }

        composable(Route.ADD_BIO) {
            AddBioScreen(
                onBack = { navController.navigateUp() }
            )
        }
    }
}