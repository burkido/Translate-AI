package com.bapps.translator.voicetotext.domain

import com.bapps.translator.core.domain.util.CommonStateFlow

interface SpeechToTextHandler {
    val state: CommonStateFlow<SpeechToTextHandlerState>
    fun startListening(langCode: String)
    fun stopListening()
    fun cancelListening()
    fun reset()
}