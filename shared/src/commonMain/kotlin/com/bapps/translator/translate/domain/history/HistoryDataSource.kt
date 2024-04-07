package com.bapps.translator.translate.domain.history

import com.bapps.translator.core.domain.util.CommonFlow

interface HistoryDataSource {

    fun getHistory(): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(item: HistoryItem)

    suspend fun deleteHistoryItem(id: Long)

    suspend fun clearHistory()

    suspend fun saveHistory(id: Long)

    suspend fun unSaveHistory(id: Long)

    fun getSavedHistories(): CommonFlow<List<HistoryItem>>

    suspend fun getSavedHistoryItem(id: Long): HistoryItem?

    fun clearSavedHistory()
}