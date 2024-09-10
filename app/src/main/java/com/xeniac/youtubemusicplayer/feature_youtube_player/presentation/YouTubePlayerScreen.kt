package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components.ErrorMessage
import com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components.MediaControlButton
import com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components.YoutubePlayerAndroidView
import com.xeniac.youtubemusicplayer.feature_youtube_player.services.YoutubePlayerService

@Composable
fun YouTubePlayerScreen(
    viewModel: YouTubePlayerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val youTubePlayerState by viewModel.youTubePlayerState.collectAsStateWithLifecycle()

    val constraintSet = ConstraintSet {
        val youTubePlayer = createRefFor(id = "youTubePlayer")
        val mediaControlBtn = createRefFor(id = "mediaControlBtn")
        val errorMessage = createRefFor(id = "errorMessage")

        constrain(youTubePlayer) {
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
            top.linkTo(anchor = parent.top)

            visibility = Visibility.Gone
        }

        constrain(mediaControlBtn) {
            start.linkTo(anchor = parent.start, margin = 16.dp)
            end.linkTo(anchor = parent.end, margin = 16.dp)
            top.linkTo(anchor = parent.top)
            bottom.linkTo(anchor = parent.bottom)

            horizontalChainWeight = 0.5f
            verticalChainWeight = 0.5f

            width = Dimension.value(150.dp)
            height = Dimension.value(150.dp)
        }

        constrain(errorMessage) {
            start.linkTo(anchor = parent.start, margin = 32.dp)
            end.linkTo(anchor = parent.end, margin = 32.dp)
            top.linkTo(anchor = mediaControlBtn.bottom, margin = 32.dp)

            val hasError = youTubePlayerState.errorMessage != null
            visibility = if (hasError) Visibility.Visible else Visibility.Gone
        }
    }

    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        YoutubePlayerAndroidView(
            initializeYouTubePlayer = viewModel::initializeYouTubePlayer,
            onPlayerStateCued = viewModel::updatePlayerStateToCued,
            onPlayerStateUnstarted = viewModel::updatePlayerStateToUnstarted,
            onPlayerStateBuffering = viewModel::updatePlayerStateToBuffering,
            onPlayerStatePlaying = viewModel::updatePlayerStateToPlaying,
            onPlayerStatePaused = viewModel::updatePlayerStateToPaused,
            onPlayerStateEnded = viewModel::updatePlayerStateToEnded,
            onPlayerStateUnknown = viewModel::updatePlayerStateToUnknown,
            onError = viewModel::showErrorMessage,
            modifier = Modifier.layoutId("youTubePlayer")
        )

        MediaControlButton(
            youTubePlayerState = youTubePlayerState,
            onPlayClick = {
                Intent(context, YoutubePlayerService::class.java).also {
                    it.action = YoutubePlayerService.Actions.START.toString()
                    context.startService(it)
                }

                youTubePlayerState.youTubePlayer?.play()
            },
            onPauseClick = {
                Intent(context, YoutubePlayerService::class.java).also {
                    it.action = YoutubePlayerService.Actions.STOP.toString()
                    context.startService(it)
                }

                youTubePlayerState.youTubePlayer?.pause()
            },
            modifier = Modifier.layoutId("mediaControlBtn")
        )

        ErrorMessage(
            message = youTubePlayerState.errorMessage,
            modifier = Modifier.layoutId("errorMessage")
        )
    }
}