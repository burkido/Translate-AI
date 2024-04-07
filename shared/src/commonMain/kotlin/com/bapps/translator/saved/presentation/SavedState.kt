package com.bapps.translator.saved.presentation

import com.bapps.translator.translate.presentation.UiHistoryItem

data class SavedState(
    val savedTranslations: List<UiHistoryItem> = emptyList(),
    val userMessage: String? = null
)
