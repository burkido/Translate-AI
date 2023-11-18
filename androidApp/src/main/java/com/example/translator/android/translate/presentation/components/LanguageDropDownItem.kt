package com.example.translator.android.translate.presentation.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.translator.core.domain.language.Language
import com.example.translator.core.presentation.UiLanguage

@Composable
fun LanguageDropDownItem(
    language: UiLanguage,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    DropdownMenuItem(
        onClick = onClick,
        modifier = modifier,
        text = { Text(text = language.language.langName) }
    )
}

@Preview(showBackground = true)
@Composable
fun LanguageDropDownItemPreview() {
    LanguageDropDownItem(
        language = UiLanguage(com.example.translator.R.drawable.turkish, Language.TURKISH),
        onClick = {},
        modifier = Modifier.width(200.dp)
    )
}