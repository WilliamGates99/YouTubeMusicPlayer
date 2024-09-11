package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.xeniac.youtubemusicplayer.R

@Composable
fun ErrorMessage(
    message: String?,
    modifier: Modifier = Modifier,
    errorMessageText: String = stringResource(
        id = R.string.youtube_player_error_message,
        message ?: ""
    ),
    fontSize: TextUnit = 24.sp,
    lineHeight: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.error
) {
    Text(
        text = errorMessageText,
        fontSize = fontSize,
        lineHeight = lineHeight,
        fontWeight = fontWeight,
        textAlign = textAlign,
        color = color,
        modifier = modifier
    )
}