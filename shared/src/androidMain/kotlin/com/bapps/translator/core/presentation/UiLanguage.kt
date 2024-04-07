package com.bapps.translator.core.presentation

import androidx.annotation.DrawableRes
import com.bapps.translator.R
import com.bapps.translator.core.domain.language.Language
import java.util.Locale

actual class UiLanguage(
    @DrawableRes val icon: Int,
    actual val language: Language
) {
    fun toLocale(): Locale? {
        return when (language) {
            Language.ENGLISH -> Locale.ENGLISH
            Language.CHINESE -> Locale.CHINESE
            Language.FRENCH -> Locale.FRENCH
            Language.GERMAN -> Locale.GERMAN
            Language.ITALIAN -> Locale.ITALIAN
            else -> null
        }
    }

    actual companion object {
        actual fun byCode(code: String): UiLanguage {
            return allLanguages.find { it.language.langCode == code }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }

        actual val allLanguages: List<UiLanguage>
            get() = Language.values().map { language ->
                UiLanguage(
                    language = language,
                    icon = when (language) {
                        Language.ENGLISH -> R.drawable.english
                        Language.ARABIC -> R.drawable.arabic
                        Language.AZERBAIJANI -> R.drawable.azerbaijani
                        Language.CHINESE -> R.drawable.chinese
                        Language.FRENCH -> R.drawable.french
                        Language.GERMAN -> R.drawable.german
                        Language.HINDI -> R.drawable.hindi
                        Language.ITALIAN -> R.drawable.italian
                        Language.RUSSIAN -> R.drawable.russian
                        Language.SPANISH -> R.drawable.spanish
                        Language.TURKISH -> R.drawable.turkish
                    }
                )
            }.sortedBy { it.language.langName }
    }
}