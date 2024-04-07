package com.bapps.translator.android.translate.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bapps.translator.core.presentation.UiLanguage

@Composable
fun SmallLanguageIcon(
    language: UiLanguage,
) {
    AsyncImage(
        model = language.icon,
        contentDescription = language.language.langName,    // TODO: think about language.language
        modifier = Modifier.size(24.dp)
    )
}