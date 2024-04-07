package com.bapps.translator.android.voicetotext.data

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.bapps.translator.android.R
import com.bapps.translator.core.domain.util.CommonStateFlow
import com.bapps.translator.core.domain.util.asCommonStateFlow
import com.bapps.translator.voicetotext.domain.SpeechToTextHandler
import com.bapps.translator.voicetotext.domain.SpeechToTextHandlerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class AndroidSpeechToTextHandler(
    private val app: Application
) : SpeechToTextHandler, RecognitionListener {

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(app)

    private val _state = MutableStateFlow(SpeechToTextHandlerState())
    override val state: CommonStateFlow<SpeechToTextHandlerState>
        get() = _state.asCommonStateFlow()

    override fun startListening(langCode: String) {
        _state.update { SpeechToTextHandlerState() }

        if (!SpeechRecognizer.isRecognitionAvailable(app)) {
            _state.update {
                it.copy(
                    error = app.getString(R.string.speech_recognition_not_available)
                )
            }
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, langCode)
        }
        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)

        _state.update { it.copy(isSpeaking = true) }
    }

    override fun stopListening() {
        _state.update { SpeechToTextHandlerState() }
        recognizer.cancel()
    }

    override fun cancelListening() {
        recognizer.cancel()
    }

    override fun reset() {
        _state.update { SpeechToTextHandlerState() }
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        _state.update { it.copy(error = null) }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rms: Float) {
        _state.update {
            it.copy(
                ratio = rms * (1f / (12f - (-2f)))
            )
        }
    }

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update { it.copy(isSpeaking = false) }
    }

    override fun onError(code: Int) {
        _state.update { it.copy(error = "An error occurred with code $code") }
    }

    override fun onResults(result: Bundle?) {
        result
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { text ->
                _state.update { it.copy(
                    result = text
                ) }
            }
    }

    override fun onPartialResults(p0: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?) = Unit

}