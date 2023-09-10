package com.example.translator.voicetotext.domain

import com.example.translator.core.domain.util.CommonStateFlow

interface VoiceToTextHandler {
    val state: CommonStateFlow<VoiceToTextState>
    fun startListening(langCode: String)
    fun stopListening()
    fun cancelListening()
    fun reset()
}