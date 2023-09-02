package com.example.translator.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.translate.domain.history.HistoryDataSource
import com.example.translator.translate.domain.translate.TranslateUseCase
import com.example.translator.translate.presentation.TranslateEvent
import com.example.translator.translate.presentation.TranslateViewModel
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