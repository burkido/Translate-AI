package com.bapps.translator.translate.presentation

import com.bapps.translator.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage,
    val isSaved: Boolean = false
)
