package com.bapps.translator.voicetotext.presentation

data class SpeechToTextState(
    val ratio: List<Float> = emptyList(),
    val spokenText : String = "",
    val recordable: Boolean = false,
    val error: String? = null,
    val displayState: DisplayState? = null
)

enum class DisplayState {
    IDLE,
    SPEAKING,
    DISPLAYING_RESULT,
    ERROR
}
