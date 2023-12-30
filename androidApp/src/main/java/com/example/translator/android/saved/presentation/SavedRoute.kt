package com.example.translator.android.saved.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.translator.android.R
import com.example.translator.android.translate.presentation.components.TranslateHistoryItem
import com.example.translator.saved.presentation.SavedEvent
import com.example.translator.saved.presentation.SavedState

@Composable
fun SavedRoute(
    state: SavedState,
    onEvent: (SavedEvent) -> Unit
) {
    Scaffold(
        topBar = { SavedTopBar(onEvent = onEvent)
        },
    ) { paddingValues ->
        SavedScreen(
            innerPadding = paddingValues,
            state = state,
            onEvent = onEvent
        )
    }
}

@Composable
fun SavedScreen(
    innerPadding: PaddingValues,
    state: SavedState,
    onEvent: (SavedEvent) -> Unit,
) {
    Log.d("save-test", "SavedScreen: ${state.savedTranslations}")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = state.savedTranslations.size,
            key = { index -> state.savedTranslations[index].id },
            itemContent = { index ->
                val item = state.savedTranslations[index]
                TranslateHistoryItem(
                    item = item,
                    onClick = { },
                    onSaveClick = { onEvent(SavedEvent.ToggleTranslationSaveStatus(item.id)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

@Composable
fun SavedTopBar(
    onEvent: (SavedEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.saved),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onEvent(SavedEvent.DeleteAllTranslations) }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.delete_all),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun SavedTopBarPreview() {
    MaterialTheme {
        SavedTopBar(onEvent = {})
    }
}
