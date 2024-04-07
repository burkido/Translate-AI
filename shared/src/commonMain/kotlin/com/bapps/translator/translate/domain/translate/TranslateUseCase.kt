package com.bapps.translator.translate.domain.translate

import com.bapps.translator.core.domain.language.Language
import com.bapps.translator.core.domain.util.Resource
import com.bapps.translator.translate.domain.history.HistoryDataSource
import com.bapps.translator.translate.domain.history.HistoryItem

class TranslateUseCase(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend fun invoke(
        fromLanguage: Language,
        toLanguage: Language,
        fromText: String
    ): Resource<String> {
        return try {
            val result = client.translate(
                text = fromText,
                sourceLanguage = fromLanguage.langCode,
                targetLanguage = toLanguage.langCode
            )

            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    toLanguageCode = toLanguage.langCode,
                    fromText = fromText,
                    toText = result
                )
            )

            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}