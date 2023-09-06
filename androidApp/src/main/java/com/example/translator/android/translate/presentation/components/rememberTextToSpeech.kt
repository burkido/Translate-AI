package com.example.translator.android.translate.presentation.components

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

// remember for tts created because after using there are some functions to run
@Composable
fun rememberTextToSpeech(): TextToSpeech {
    val context = LocalContext.current

    val textToSpeech = remember {
        TextToSpeech(context, null)
    }

    // callback after our composable leaves from composition
    DisposableEffect(key1 = textToSpeech) {
        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()

        }
    }
    return textToSpeech
}