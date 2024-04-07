package com.example.translator.translate.data.translate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslatedDto(
    @SerialName("translated_text")
    val translatedText: String
)
