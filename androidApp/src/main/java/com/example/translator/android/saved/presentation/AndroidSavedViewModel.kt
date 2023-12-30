package com.example.translator.android.saved.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.saved.presentation.SavedEvent
import com.example.translator.saved.presentation.SavedViewModel
import com.example.translator.translate.domain.history.HistoryDataSource
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

    fun onEvent(event: SavedEvent) {
        viewModel.value.onEvent(event)
    }

}