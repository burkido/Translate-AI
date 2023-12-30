package com.example.translator.saved.presentation

sealed class SavedEvent {

    data class SaveTranslation(val id: Long) : SavedEvent()
    object DeleteAllTranslations : SavedEvent()
    data class DeleteTranslationById(val id: Long) : SavedEvent()
    object OnErrorSeen : SavedEvent()
}