package com.example.translator.translate.presentation

import com.example.translator.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage,
    val isSaved: Boolean = false
)
