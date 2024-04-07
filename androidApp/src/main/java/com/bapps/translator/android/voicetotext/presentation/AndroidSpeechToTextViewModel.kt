package com.bapps.translator.android.voicetotext.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bapps.translator.voicetotext.domain.SpeechToTextHandler
import com.bapps.translator.voicetotext.presentation.SpeechToTextEvent
import com.bapps.translator.voicetotext.presentation.SpeechToTextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidSpeechToTextViewModel @Inject constructor(
    private val speechToTextHandler: SpeechToTextHandler
) : ViewModel() {

    private val viewModel by lazy {
        SpeechToTextViewModel(
            speechToTextHandler = speechToTextHandler,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: SpeechToTextEvent) {
        viewModel.onEvent(event)
    }
}