package com.bapps.translator.android.di

import android.app.Application
import com.bapps.translator.database.TranslateDatabase
import com.bapps.translator.translate.data.local.DatabaseDriverFactory
import com.bapps.translator.translate.data.local.history.SqlDelightHistoryDataSource
import com.bapps.translator.translate.data.remote.HttpClientFactory
import com.bapps.translator.translate.data.translate.KtorTranslateClient
import com.bapps.translator.translate.domain.history.HistoryDataSource
import com.bapps.translator.translate.domain.translate.TranslateClient
import com.bapps.translator.translate.domain.translate.TranslateUseCase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        translateClient: TranslateClient,
        historyDataSource: HistoryDataSource
    ): TranslateUseCase {
        return TranslateUseCase(translateClient, historyDataSource)
    }
}