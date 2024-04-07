package com.bapps.translator.android.saved.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bapps.translator.android.core.components.TopLevelDestinations
import com.bapps.translator.android.saved.presentation.AndroidSavedViewModel
import com.bapps.translator.android.saved.presentation.SavedRoute

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
            val viewModel = hiltViewModel<AndroidSavedViewModel>()
            val state by viewModel.uiState.collectAsState()

            SavedRoute(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}