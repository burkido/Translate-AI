package com.bapps.translator.android.voicetotext.di

import android.app.Application
import com.bapps.translator.android.voicetotext.data.AndroidSpeechToTextHandler
import com.bapps.translator.voicetotext.domain.SpeechToTextHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VoiceToTextModule {

    @Provides
    @ViewModelScoped
    fun provideVoiceToTextHandler(
         app: Application
    ): SpeechToTextHandler {
        return AndroidSpeechToTextHandler(app = app)
    }
}