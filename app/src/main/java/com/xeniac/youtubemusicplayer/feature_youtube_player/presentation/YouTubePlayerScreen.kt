package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
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

@Composable
fun YouTubePlayerScreen(
    viewModel: YouTubePlayerViewModel = hiltViewModel()
) {
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
            top.linkTo(anchor = mediaControlBtn.bottom, margin = 44.dp)

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
            initializeYouTubePlayer = { player ->
                viewModel.onAction(YouTubePlayerAction.InitializeYouTubePlayer(player))
            },
            onPlayerStateCued = {
                viewModel.onAction(YouTubePlayerAction.UpdatePlayerStateToCued)
            },
            onPlayerStateUnstarted = {
                viewModel.onAction(YouTubePlayerAction.UpdatePlayerStateToUnstarted)
            },
            onPlayerStateBuffering = {
                viewModel.onAction(YouTubePlayerAction.UpdatePlayerStateToBuffering)
            },
            onPlayerStatePlaying = {
                viewModel.onAction(YouTubePlayerAction.UpdatePlayerStateToPlaying)
            },
            onPlayerStatePaused = {
                viewModel.onAction(YouTubePlayerAction.UpdatePlayerStateToPaused)
            },
            onPlayerStateEnded = {
                viewModel.onAction(YouTubePlayerAction.UpdatePlayerStateToEnded)
            },
            onPlayerStateUnknown = {
                viewModel.onAction(YouTubePlayerAction.UpdatePlayerStateToUnknown)
            },
            onError = { message ->
                viewModel.onAction(YouTubePlayerAction.ShowErrorMessage(message))
            },
            modifier = Modifier.layoutId("youTubePlayer")
        )

        MediaControlButton(
            youTubePlayerState = youTubePlayerState,
            onPlayClick = {
                viewModel.onAction(YouTubePlayerAction.PlayVideo)
            },
            onPauseClick = {
                viewModel.onAction(YouTubePlayerAction.PauseVideo)
            },
            modifier = Modifier.layoutId("mediaControlBtn")
        )

        ErrorMessage(
            message = youTubePlayerState.errorMessage,
            modifier = Modifier.layoutId("errorMessage")
        )
    }
}