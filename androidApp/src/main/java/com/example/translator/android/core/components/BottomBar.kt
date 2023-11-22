package com.example.translator.android.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.translator.android.R

enum class TopLevelDestinations(
    val route: String,
    val icon: ImageVector,
    val iconTextId: Int
) {
    TRANSLATE(
        route = "TRANSLATE",
        icon = Icons.Default.Home,
        iconTextId = R.string.tab_translate
    ),
    SAVED(
        route = "SAVED",
        icon = Icons.Default.Favorite,
        iconTextId = R.string.saved
    ),
}

class NavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestinations) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}