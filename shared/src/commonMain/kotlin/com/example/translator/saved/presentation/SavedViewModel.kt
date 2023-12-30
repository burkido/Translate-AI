package com.example.translator.saved.presentation

import com.example.translator.core.presentation.UiLanguage
import com.example.translator.translate.presentation.UiHistoryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine

class SavedViewModel(

    private val coroutineScope: CoroutineScope?,
    historyDataSource: Any
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

//    private val _state = MutableStateFlow(SavedState())
//    val state = combine(
//        _state,
//        historyDataSource.getSavedTranslations()
//    ) { state, savedTranslations ->
//        if (state.savedTranslations != savedTranslations) {
//            state.copy(
//                savedTranslations = savedTranslations.mapNotNull { item ->
//                    item.id?.let {
//                        UiHistoryItem(
//                            id = it,
//                            fromText = item.fromText,
//                            toText = item.toText,
//                            fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
//                            toLanguage = UiLanguage.byCode(item.toLanguageCode)
//                        )
//                    }
//                }
//            )
//        } else
//            state
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = SavedState()
//    ).asCommonStateFlow()
}