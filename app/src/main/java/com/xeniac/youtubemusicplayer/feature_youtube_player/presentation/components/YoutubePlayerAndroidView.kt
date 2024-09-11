package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayerAndroidView(
    modifier: Modifier = Modifier,
    initializeYouTubePlayer: (player: YouTubePlayer) -> Unit,
    isBackgroundPlaybackEnabled: Boolean = true,
    onPlayerStateCued: () -> Unit,
    onPlayerStateUnstarted: () -> Unit,
    onPlayerStateBuffering: () -> Unit,
    onPlayerStatePlaying: () -> Unit,
    onPlayerStatePaused: () -> Unit,
    onPlayerStateEnded: () -> Unit,
    onPlayerStateUnknown: () -> Unit,
    onError: (message: String) -> Unit
) {
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                enableBackgroundPlayback(isBackgroundPlaybackEnabled)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        initializeYouTubePlayer(youTubePlayer)
                    }

                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                        super.onStateChange(youTubePlayer, state)

                        when (state) {
                            PlayerConstants.PlayerState.VIDEO_CUED -> onPlayerStateCued()
                            PlayerConstants.PlayerState.UNSTARTED -> onPlayerStateUnstarted()
                            PlayerConstants.PlayerState.BUFFERING -> onPlayerStateBuffering()
                            PlayerConstants.PlayerState.PLAYING -> onPlayerStatePlaying()
                            PlayerConstants.PlayerState.PAUSED -> onPlayerStatePaused()
                            PlayerConstants.PlayerState.ENDED -> onPlayerStateEnded()
                            PlayerConstants.PlayerState.UNKNOWN -> onPlayerStateUnknown()
                        }
                    }

                    override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError
                    ) {
                        super.onError(youTubePlayer, error)
                        onError(error.toString())
                    }
                })
            }
        },
        modifier = modifier
    )
}