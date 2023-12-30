package com.example.translator.translate.domain.history

import com.example.translator.core.domain.util.CommonFlow

interface HistoryDataSource {

    fun getHistory(): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(item: HistoryItem)

    suspend fun deleteHistoryItem(id: Long)

    suspend fun clearHistory()

    suspend fun saveHistory(id: Long)

    suspend fun unSaveHistory(id: Long)

    fun getSavedHistories(): CommonFlow<List<HistoryItem>>

    fun clearSavedHistories()
}