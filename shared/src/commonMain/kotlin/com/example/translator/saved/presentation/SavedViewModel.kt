package com.example.translator.saved.presentation

import com.example.translator.core.domain.util.asCommonStateFlow
import com.example.translator.core.presentation.UiLanguage
import com.example.translator.translate.domain.history.HistoryDataSource
import com.example.translator.translate.presentation.UiHistoryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedViewModel(
    private val historyDataSource: HistoryDataSource,
    coroutineScope: CoroutineScope?,
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(SavedState())
    val state = combine(
        _state,
        historyDataSource.getSavedHistories()
    ) { state, savedTranslations ->
        if (state.savedTranslations != savedTranslations) {
            state.copy(
                savedTranslations = savedTranslations.mapNotNull { item ->
                    item.id?.let {
                        UiHistoryItem(
                            id = it,
                            fromText = item.fromText,
                            toText = item.toText,
                            fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                            toLanguage = UiLanguage.byCode(item.toLanguageCode),
                            isSaved = item.isSaved
                        )
                    }
                }
            )
        } else
            state
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SavedState()
    ).asCommonStateFlow()

    fun onEvent(event: SavedEvent) {
        when (event) {
            SavedEvent.DeleteAllTranslations -> {
                viewModelScope.launch {
                    historyDataSource.clearSavedHistory()
                }
            }

            SavedEvent.OnErrorSeen -> {
                _state.update { it.copy(userMessage = null) }
            }

            is SavedEvent.ToggleTranslationSaveStatus -> {
                viewModelScope.launch {
                    val item = historyDataSource.getSavedHistoryItem(event.id)
                    if (item != null) {
                        if (item.isSaved) {
                            historyDataSource.unSaveHistory(event.id)
                        } else {
                            historyDataSource.saveHistory(event.id)
                        }
                    }
                }
            }
        }
    }
}