package com.example.translator.android.saved.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.translator.android.core.components.TopLevelDestinations
import com.example.translator.android.saved.presentation.SavedRoute

const val saved_route = "saved_route"

fun NavController.navigateToSaved() {
    this.navigate(saved_route) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.savedGraph() {
    navigation(
        route = saved_route,
        startDestination = TopLevelDestinations.SAVED.route
    ) {
        composable(route = TopLevelDestinations.SAVED.route) {
            SavedRoute()
        }
    }

}