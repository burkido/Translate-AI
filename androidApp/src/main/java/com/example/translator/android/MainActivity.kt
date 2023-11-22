package com.example.translator.android

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.translator.android.core.components.BottomNavigationBar
import com.example.translator.android.core.components.NavigationActions
import com.example.translator.android.core.components.TopLevelDestinations
import com.example.translator.android.core.presentation.Routes
import com.example.translator.android.translate.presentation.AndroidTranslateViewModel
import com.example.translator.android.translate.presentation.TranslateScreen
import com.example.translator.android.voicetotext.presentation.AndroidSpeechToTextViewModel
import com.example.translator.android.voicetotext.presentation.SpeechToTextScreen
import com.example.translator.translate.presentation.TranslateEvent
import com.example.translator.voicetotext.presentation.SpeechToTextEvent
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
        startDestination = Routes.TRANSLATE,
        modifier = modifier
    ) {
        // TODO: add nav graph builder here
        composable(route = Routes.TRANSLATE) {
            val viewModel = hiltViewModel<AndroidTranslateViewModel>()
            val state by viewModel.uiState.collectAsState()

            val voiceResult by it
                .savedStateHandle
                .getStateFlow<String?>("voiceResult", null)
                .collectAsState()

            LaunchedEffect(voiceResult) {
                viewModel.onEvent(TranslateEvent.SubmitVoiceResult(voiceResult))
                it.savedStateHandle["voiceResult"] = null
            }
            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is TranslateEvent.RecordAudio -> navController.navigate(Routes.VOICE_TO_TEXT + "/${state.fromLanguage.language.langCode}")
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }

        composable(
            route = Routes.VOICE_TO_TEXT + "/{languageCode}",
            arguments = listOf(
                navArgument("languageCode") {
                    type = NavType.StringType
                    defaultValue = "en"
                }
            )
        ) { backStackEntry ->
            val languageCode = backStackEntry.arguments?.getString("languageCode") ?: "en"
            val viewModel = hiltViewModel<AndroidSpeechToTextViewModel>()
            val state by viewModel.state.collectAsState()

            SpeechToTextScreen(
                state = state,
                languageCode = languageCode,
                onResult = { text ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("voiceResult", text)
                    navController.popBackStack()
                },
                onEvent = { event ->
                    when (event) {
                        is SpeechToTextEvent.Close -> navController.popBackStack()
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}