package com.example.translator.saved.presentation

sealed class SavedEvent {

    object DeleteAllTranslations : SavedEvent()
    object OnErrorSeen : SavedEvent()
}