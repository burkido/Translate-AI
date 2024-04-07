package com.bapps.translator.android.saved.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bapps.translator.saved.presentation.SavedEvent
import com.bapps.translator.saved.presentation.SavedViewModel
import com.bapps.translator.translate.domain.history.HistoryDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidSavedViewModel @Inject constructor(
    private val historyDataSource: HistoryDataSource
) : ViewModel() {

    private val viewModel = lazy {
        SavedViewModel(
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val uiState = viewModel.value.state

    fun onEvent(event: SavedEvent) {
        viewModel.value.onEvent(event)
    }

}