package com.bapps.translator.android.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(
    destinations: List<TopLevelDestinations>,
    selectedDestination: String,
    onNavigateToTopLevelDestination: (TopLevelDestinations) -> Unit,
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        destinations.forEach { destination ->
            //val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selectedDestination == destination.route,
                onClick = {
                    if (selectedDestination != destination.route) {
                        onNavigateToTopLevelDestination(destination)
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(id = destination.iconTextId)
                    )
                },
            )
        }
    }
}