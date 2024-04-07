package com.bapps.translator.translate.domain.translate

interface TranslateClient {
    
    suspend fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): String
}