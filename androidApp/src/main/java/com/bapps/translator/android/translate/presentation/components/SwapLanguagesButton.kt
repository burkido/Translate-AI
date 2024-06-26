package com.bapps.translator.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.bapps.translator.android.R

@Composable
fun SwapLanguagesButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_swap_horiz_24),
            contentDescription = stringResource(id = R.string.swap_languages),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}