package com.bapps.translator.translate.data.local.history

import com.bapps.translator.core.domain.util.CommonFlow
import com.bapps.translator.core.domain.util.asCommonFlow
import com.bapps.translator.database.TranslateDatabase
import com.bapps.translator.translate.domain.history.HistoryDataSource
import com.bapps.translator.translate.domain.history.HistoryItem
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    db: TranslateDatabase
) : HistoryDataSource {

    private val queries = db.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { history ->
                history.map { it.toHistoryItem() }
            }
            .asCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        queries.insertHistoryEntitiy(
            id = item.id,
            fromLanguageCode = item.fromLanguageCode,
            toLanguageCode = item.toLanguageCode,
            fromText = item.fromText,
            toText = item.toText,
            isSaved = if (item.isSaved) 1L else 0L,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }

    override suspend fun deleteHistoryItem(id: Long) {
        queries.deleteHistoryEntity(id = id)
    }

    override suspend fun clearHistory() {
        queries.clearHistory()
    }

    override suspend fun saveHistory(id: Long) {
        queries.saveHistoryEntity(id = id)
    }

    override suspend fun unSaveHistory(id: Long) {
        queries.unSaveHistoryEntity(id = id)
    }

    override fun getSavedHistories(): CommonFlow<List<HistoryItem>> {
        return queries
            .getSavedHistory()
            .asFlow()
            .mapToList()
            .map { history ->
                history.map { it.toHistoryItem() }
            }
            .asCommonFlow()
    }

    override suspend fun getSavedHistoryItem(id: Long): HistoryItem? {
        return queries.getHistoryEntity(id = id).executeAsOneOrNull()?.toHistoryItem()
    }

    override fun clearSavedHistory() {
        queries.clearSavedHistory()
    }
}