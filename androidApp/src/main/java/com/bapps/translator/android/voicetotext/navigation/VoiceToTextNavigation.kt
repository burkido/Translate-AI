package com.bapps.translator.android.voicetotext.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bapps.translator.android.voicetotext.presentation.AndroidSpeechToTextViewModel
import com.bapps.translator.android.voicetotext.presentation.SpeechToTextScreen
import com.bapps.translator.voicetotext.presentation.SpeechToTextEvent

const val voice_to_text_route = "voice_to_text_route"

fun NavController.navigateToVoiceToText() {
    this.navigate(voice_to_text_route) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.voiceToTextGraph(
    navController: NavHostController,
) {
    composable(
        route = "$voice_to_text_route/{languageCode}",
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