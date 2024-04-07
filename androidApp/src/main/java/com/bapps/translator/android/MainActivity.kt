package com.bapps.translator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bapps.translator.android.core.components.BottomNavigationBar
import com.bapps.translator.android.core.components.NavigationActions
import com.bapps.translator.android.core.components.TopLevelDestinations
import com.bapps.translator.android.saved.navigation.savedGraph
import com.bapps.translator.android.translate.presentation.navigation.translateGraph
import com.bapps.translator.android.translate.presentation.navigation.translate_route
import com.bapps.translator.android.voicetotext.navigation.voiceToTextGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) { NavigationActions(navController) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: TopLevelDestinations.TRANSLATE.route

    AppContent(
        navController = navController,
        selectedDestination = selectedDestination,
        navigateToTopLevelDestination = navigationActions::navigateTo
    )
}

@Composable
fun AppContent(
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (TopLevelDestinations) -> Unit,
) {
    val showBottomBar = remember {
        mutableStateOf(true)
    }

    val bottomBarRoutes = listOf(
        TopLevelDestinations.TRANSLATE.route,
        TopLevelDestinations.SAVED.route,
    )

    navController.addOnDestinationChangedListener { _, destination, _ ->
        showBottomBar.value = destination.route in bottomBarRoutes
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface),
            verticalArrangement = Arrangement.Bottom
        ) {
            TranslateRoot(
                navController = navController,
                modifier = Modifier.weight(1f),
            )
            AnimatedVisibility(visible = showBottomBar.value) {
                BottomNavigationBar(
                    destinations = TopLevelDestinations.values().toList(),
                    selectedDestination = selectedDestination,
                    onNavigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}

@Composable
fun TranslateRoot(
    modifier: Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = translate_route,
        modifier = modifier
    ) {
        translateGraph(navController = navController,)
        savedGraph()
        voiceToTextGraph(navController = navController)
    }
}