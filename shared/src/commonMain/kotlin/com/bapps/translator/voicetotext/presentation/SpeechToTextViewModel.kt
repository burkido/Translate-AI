package com.bapps.translator.voicetotext.presentation

import com.bapps.translator.core.domain.util.asCommonStateFlow
import com.bapps.translator.voicetotext.domain.SpeechToTextHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpeechToTextViewModel(
    private val speechToTextHandler: SpeechToTextHandler,
    private val coroutineScope: CoroutineScope? = null
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(SpeechToTextState())
    val state = _state.combine(speechToTextHandler.state) { state, speechResult ->
        state.copy(
            spokenText = speechResult.result,
            error = if (state.recordable) speechResult.error else "Can't record without permission",
            displayState = when {
                !state.recordable || speechResult.error != null -> DisplayState.ERROR
                speechResult.result.isNotBlank() && !speechResult.isSpeaking -> DisplayState.DISPLAYING_RESULT
                speechResult.isSpeaking -> DisplayState.SPEAKING
                else -> DisplayState.IDLE
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SpeechToTextState()
    ).asCommonStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                if (state.value.displayState == DisplayState.SPEAKING) {
                    _state.update {
                        it.copy(
                            ratio = it.ratio + speechToTextHandler.state.value.ratio
                        )
                    }
                }
                delay(50)
            }
        }
    }

    fun onEvent(event: SpeechToTextEvent) {
        when (event) {
            SpeechToTextEvent.Close -> {
                Unit
            }

            is SpeechToTextEvent.PermissionResult -> {
                _state.update { it.copy(recordable = event.isGranted) }
            }

            SpeechToTextEvent.Reset -> {
                speechToTextHandler.reset()
                _state.update { SpeechToTextState() }
            }

            is SpeechToTextEvent.ToggleRecording -> toggleRecording(event.langCode)
        }
    }

    private fun toggleRecording(langCode: String) {
        _state.update { it.copy(ratio = emptyList()) }
        speechToTextHandler.cancelListening()
        if (state.value.displayState == DisplayState.SPEAKING)
            speechToTextHandler.stopListening()
        else
            speechToTextHandler.startListening(langCode)
    }
}