package com.bapps.translator.android.translate.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bapps.translator.android.core.components.TopLevelDestinations
import com.bapps.translator.android.translate.presentation.AndroidTranslateViewModel
import com.bapps.translator.android.translate.presentation.TranslateScreen
import com.bapps.translator.android.voicetotext.navigation.voice_to_text_route
import com.bapps.translator.translate.presentation.TranslateEvent

const val translate_route = "translate_route"

fun NavController.navigateToTranslate() {
    this.navigate(translate_route) {
        popUpTo(0)
    }
}

fun NavGraphBuilder.translateGraph(
    navController: NavHostController,
) {
    navigation(
        route = translate_route,
        startDestination = TopLevelDestinations.TRANSLATE.route
    ) {
        composable(route = TopLevelDestinations.TRANSLATE.route) {
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
                        is TranslateEvent.RecordAudio -> navController.navigate(voice_to_text_route + "/${state.fromLanguage.language.langCode}")
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}