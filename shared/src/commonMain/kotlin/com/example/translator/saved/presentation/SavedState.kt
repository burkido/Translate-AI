package com.example.translator.saved.presentation

import com.example.translator.translate.presentation.UiHistoryItem

data class SavedState(
    val savedTranslations: List<UiHistoryItem> = emptyList(),
    val userMessage: String? = null
)
