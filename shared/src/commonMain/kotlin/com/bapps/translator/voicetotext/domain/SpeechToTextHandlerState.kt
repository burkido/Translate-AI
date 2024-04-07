package com.bapps.translator.voicetotext.domain

data class SpeechToTextHandlerState(
    val result: String = "",
    val ratio: Float = 0f,
    val isSpeaking: Boolean = false,
    val error: String? = null,
)
