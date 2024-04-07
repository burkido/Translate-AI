package com.bapps.translator.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bapps.translator.translate.domain.history.HistoryDataSource
import com.bapps.translator.translate.domain.translate.TranslateUseCase
import com.bapps.translator.translate.presentation.TranslateEvent
import com.bapps.translator.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translate: TranslateUseCase,
    private val historyDataSource: HistoryDataSource
) : ViewModel() {

    private val viewModel = lazy {
        TranslateViewModel(
            translate = translate,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val uiState = viewModel.value.state

    fun onEvent(event: TranslateEvent) {
        viewModel.value.onEvent(event)
    }

}