package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xeniac.youtubemusicplayer.R
import com.xeniac.youtubemusicplayer.core.presentation.utils.toPx
import com.xeniac.youtubemusicplayer.core.ui.components.pulseEffect
import com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.states.YouTubePlayerState

@Composable
fun MediaControlButton(
    youTubePlayerState: YouTubePlayerState,
    modifier: Modifier = Modifier,
    progressIndicatorColor: Color = Color.White,
    btnSize: Dp = 150.dp,
    btnSizePx: Float = btnSize.toPx(),
    btnColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary,
        disabledContentColor = MaterialTheme.colorScheme.onPrimary
    ),
    pulseEffectColor: Color = btnColors.containerColor,
    btnText: String = if (!youTubePlayerState.isVideoCued) {
        stringResource(id = R.string.youtube_player_btn_preparing)
    } else {
        if (youTubePlayerState.isPlaying) {
            stringResource(id = R.string.youtube_player_btn_pause)
        } else stringResource(id = R.string.youtube_player_btn_play)
    },
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Button(
            enabled = youTubePlayerState.isVideoCued && !youTubePlayerState.isBuffering,
            colors = btnColors,
            onClick = {
                if (youTubePlayerState.isPlaying) {
                    onPauseClick()
                } else onPlayClick()
            },
            modifier = Modifier
                .size(btnSize)
                .addPulseEffects(
                    isPlaying = youTubePlayerState.isPlaying,
                    btnSizePx = btnSizePx,
                    pulseEffectColor = pulseEffectColor
                )
        ) {
            if (youTubePlayerState.isBuffering) {
                CircularProgressIndicator(
                    color = progressIndicatorColor
                )
            } else {
                Text(text = btnText)
            }
        }
    }
}

fun Modifier.addPulseEffects(
    isPlaying: Boolean,
    btnSizePx: Float,
    pulseEffectColor: Color
): Modifier = composed {
    if (isPlaying) {
        this
            .pulseEffect(
                initialSize = btnSizePx / 2,
                targetSize = btnSizePx * 0.80f,
                pulseColor = pulseEffectColor
            )
            .pulseEffect(
                initialSize = btnSizePx / 2,
                targetSize = btnSizePx * 0.80f,
                pulseColor = pulseEffectColor,
                startDelayInMs = 1000
            )
            .pulseEffect(
                initialSize = btnSizePx / 2,
                targetSize = btnSizePx * 0.80f,
                pulseColor = pulseEffectColor,
                startDelayInMs = 2000
            )
    } else this
}