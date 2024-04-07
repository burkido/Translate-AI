package com.bapps.translator.translate.data.translate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslateDto(
    @SerialName("text") val textToTranslate: String,
    @SerialName("source_lang") val sourceLanguageCode: String,
    @SerialName("target_lang") val targetLanguageCode: String,
)
