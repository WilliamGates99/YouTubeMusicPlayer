package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.xeniac.youtubemusicplayer.R
import com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.states.YouTubePlayerState

@Composable
fun MediaControlButton(
    youTubePlayerState: YouTubePlayerState,
    modifier: Modifier = Modifier,
    progressIndicatorColor: Color = Color.White,
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
    Button(
        enabled = youTubePlayerState.isVideoCued && !youTubePlayerState.isBuffering,
        onClick = {
            if (youTubePlayerState.isPlaying) {
                onPauseClick()
            } else onPlayClick()
        },
        modifier = modifier
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