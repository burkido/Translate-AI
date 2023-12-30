package com.example.translator.android.saved.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.translator.saved.presentation.SavedEvent
import com.example.translator.saved.presentation.SavedState

@Composable
fun SavedRoute(
    state: SavedState,
    onEvent: (SavedEvent) -> Unit
) {

    SavedScreen()
}

@Composable
fun SavedScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Saved screen")
    }

}