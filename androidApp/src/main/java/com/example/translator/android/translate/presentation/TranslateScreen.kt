package com.example.translator.android.translate.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.translator.android.R
import com.example.translator.android.translate.presentation.components.LanguageDropDown
import com.example.translator.android.translate.presentation.components.SmallLanguageIcon
import com.example.translator.android.translate.presentation.components.SwapLanguagesButton
import com.example.translator.android.translate.presentation.components.TranslateTextField
import com.example.translator.android.translate.presentation.components.gradientSurface
import com.example.translator.core.presentation.UiLanguage
import com.example.translator.translate.domain.translate.TranslateError
import com.example.translator.translate.presentation.TranslateEvent
import com.example.translator.translate.presentation.TranslateState
import com.example.translator.translate.presentation.UiHistoryItem

@Composable
fun TranslateHistoryItem(
    item: UiHistoryItem,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .gradientSurface()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallLanguageIcon(language = item.fromLanguage)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.fromText,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.width(16.dp))
            SmallLanguageIcon(language = item.toLanguage)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.toText,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(id = R.string.cd_add_to_favorite),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun TranslateScreen(
    state: TranslateState,
    onEvent: (TranslateEvent) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        val message = when (state.error) {
            TranslateError.SERVICE_UNAVAILABLE -> context.getString(R.string.error_service_unavailable)
            TranslateError.CLIENT_ERROR -> context.getString(R.string.client_error)
            TranslateError.SERVER_ERROR -> context.getString(R.string.server_error)
            TranslateError.UNKNOWN -> context.getString(R.string.unknown_error)
            else -> null
        }
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            onEvent(TranslateEvent.OnErrorSeen)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(TranslateEvent.RecordAudio) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_mic_24),
                    contentDescription = "Record Audio",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LanguageDropDown(
                        language = state.fromLanguage,
                        isOpen = state.isChoosingFromLanguage,
                        onClick = { onEvent(TranslateEvent.OpenFromLanguageDropdown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseFromLanguage(it)) },
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SwapLanguagesButton(onClick = { onEvent(TranslateEvent.SwapLanguages) })
                    Spacer(modifier = Modifier.weight(1f))
                    LanguageDropDown(
                        language = state.toLanguage,
                        isOpen = state.isChoosingToLanguage,
                        onClick = { onEvent(TranslateEvent.OpenToLanguageDropdown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseToLanguage(it)) },
                    )
                }
            }

            item {
                val clipboardManager = LocalClipboardManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                TranslateTextField(
                    fromText = state.fromText,
                    toText = state.toText,
                    isTranslating = state.isTranslating,
                    fromLanguage = state.fromLanguage,
                    toLanguage = state.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onEvent(TranslateEvent.Translate)
                    },
                    onTextChange = { onEvent(TranslateEvent.ChangeTranslationText(it)) },
                    onCopyClick = {
                        clipboardManager.setText(buildAnnotatedString { append(state.toText) })
                        Toast.makeText(
                            context,
                            context.getString(R.string.copied),
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    onCloseClick = { onEvent(TranslateEvent.CloseTranslation) },
                    onSpeakClick = { },
                    onTextFieldClick = { onEvent(TranslateEvent.EditTranslation) },
                )
            }

            item {
                if (state.history.isNotEmpty()) {
                    Text(text = stringResource(id = R.string.history), style = MaterialTheme.typography.bodyMedium)
                }
            }

            items(
                count = state.history.size,
                key = { index -> state.history[index].id },
                itemContent = { index ->
                    val item = state.history[index]
                    TranslateHistoryItem(
                        item = item,
                        onClick = { onEvent(TranslateEvent.SelectHistoryItem(item)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun TranslateScreenPreview() {
    TranslateScreen(
        state = TranslateState(
            fromLanguage = UiLanguage.byCode("en"),
            toLanguage = UiLanguage.byCode("tr"),
            fromText = "Hello",
            toText = "Merhaba",
            isTranslating = false,
            isChoosingFromLanguage = false,
            isChoosingToLanguage = false,
            history = listOf(
                UiHistoryItem(
                    id = 1,
                    fromLanguage = UiLanguage.byCode("en"),
                    toLanguage = UiLanguage.byCode("tr"),
                    fromText = "Hello",
                    toText = "Merhaba Merhaba Merhaba Merhaba Merhaba Merhaba Merhaba Merhaba "
                ),
                UiHistoryItem(
                    id = 2,
                    fromLanguage = UiLanguage.byCode("en"),
                    toLanguage = UiLanguage.byCode("tr"),
                    fromText = "Hello",
                    toText = "Merhaba"
                ),
                UiHistoryItem(
                    id = 3,
                    fromLanguage = UiLanguage.byCode("en"),
                    toLanguage = UiLanguage.byCode("tr"),
                    fromText = "Hello",
                    toText = "Merhaba"
                ),
                UiHistoryItem(
                    id = 4,
                    fromLanguage = UiLanguage.byCode("en"),
                    toLanguage = UiLanguage.byCode("tr"),
                    fromText = "Hello",
                    toText = "Merhaba"
                ),
                UiHistoryItem(
                    id = 5,
                    fromLanguage = UiLanguage.byCode("en"),
                    toLanguage = UiLanguage.byCode("tr"),
                    fromText = "Hello",
                    toText = "Merhaba"
                ),
                UiHistoryItem(
                    id = 6,
                    fromLanguage = UiLanguage.byCode("en"),
                    toLanguage = UiLanguage.byCode("tr"),
                    fromText = "Hello",
                    toText = "Merhaba"
                ),
                UiHistoryItem(
                    id = 7,
                    fromLanguage = UiLanguage.byCode("en"),
                    toLanguage = UiLanguage.byCode("tr"),
                    fromText = "Hello",
                    toText = "Merhaba"
                ),
                UiHistoryItem(
                    id = 8,
                    fromLanguage = UiLanguage.byCode("en"),
                    toLanguage = UiLanguage.byCode("tr"),
                    fromText = "Hello",
                    toText = "Merhaba"
                ),
            )
        ),
        onEvent = {}
    )
    
}