package com.example.translator.core.domain.language

enum class Language(
    val langCode: String,
    val langName: String
) {
    ENGLISH("en", "English"),
    ARABIC("ar", "Arabic"),
    AZERBAIJANI("az", "Azerbaijani"),
    CHINESE("zh", "Chinese"),
    FRENCH("fr", "French"),
    GERMAN("de", "German"),
    HINDI("hi", "Hindi"),
    ITALIAN("it", "Italian"),
    RUSSIAN("ru", "Russian"),
    SPANISH("es", "Spanish"),
    TURKISH("tr", "Turkish");

    companion object {
        fun byCode(code: String): Language {
            return values().find { it.langCode == code }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }
    }
}
