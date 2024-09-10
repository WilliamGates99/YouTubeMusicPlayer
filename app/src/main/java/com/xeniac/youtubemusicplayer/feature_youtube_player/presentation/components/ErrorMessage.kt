package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ErrorMessage(
    message: String?,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp,
    lineHeight: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.error
) {
    message?.let {
        Text(
            text = message,
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontWeight = fontWeight,
            textAlign = textAlign,
            color = color,
            modifier = modifier
        )
    }
}