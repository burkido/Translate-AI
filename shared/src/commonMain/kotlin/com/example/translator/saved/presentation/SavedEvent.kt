package com.example.translator.saved.presentation

sealed class SavedEvent {

    object DeleteAllTranslations : SavedEvent()
    data class DeleteTranslationById(val id: Long) : SavedEvent()
    object OnErrorSeen : SavedEvent()
}