package com.example.translator.voicetotext.domain

import com.example.translator.core.domain.util.CommonStateFlow

interface SpeechToTextHandler {
    val state: CommonStateFlow<SpeechToTextHandlerState>
    fun startListening(langCode: String)
    fun stopListening()
    fun cancelListening()
    fun reset()
}