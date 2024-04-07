package com.bapps.translator.saved.presentation

sealed class SavedEvent {

    data class ToggleTranslationSaveStatus(val id: Long) : SavedEvent()
    object DeleteAllTranslations : SavedEvent()
    object OnErrorSeen : SavedEvent()
}