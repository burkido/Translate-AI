package com.example.translator.translate.domain.history

import com.example.translator.core.domain.util.CommonFlow

interface HistoryDataSource {

    fun getHistory(): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(item: HistoryItem)

    suspend fun clearHistory()

    suspend fun saveTranslation(item: HistoryItem)
}