package com.bapps.translator.voicetotext.presentation

sealed class SpeechToTextEvent {
    object Close : SpeechToTextEvent()
    object Reset : SpeechToTextEvent()
    data class ToggleRecording(val langCode: String) : SpeechToTextEvent()
    data class PermissionResult(val isGranted: Boolean, val isPermanentlyDeclined: Boolean) : SpeechToTextEvent()
}
