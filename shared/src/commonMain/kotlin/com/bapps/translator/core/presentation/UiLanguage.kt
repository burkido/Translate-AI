package com.bapps.translator.core.presentation

import com.bapps.translator.core.domain.language.Language

expect class UiLanguage {
    val language: Language

    companion object {

        fun byCode(code: String): UiLanguage

        val allLanguages: List<UiLanguage>
    }
}