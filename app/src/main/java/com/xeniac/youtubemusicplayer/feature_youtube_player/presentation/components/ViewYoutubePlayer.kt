package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.xeniac.youtubemusicplayer.feature_youtube_player.services.YoutubePlayerService
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch
import timber.log.Timber

data class VideoPlayingState(
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val isVideoCued: Boolean = false
)

@Composable
fun ViewYoutubePlayer(
    youTubeVideoId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var ytPlayer by remember { mutableStateOf<YouTubePlayer?>(null) }
    var videoPlayingState by remember { mutableStateOf(VideoPlayingState()) }

    val constraintSet = ConstraintSet {
        val youTubePlayer = createRefFor(id = "youTubePlayer")
        val controlBtn = createRefFor(id = "controlBtn")

        constrain(youTubePlayer) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)

            visibility = Visibility.Gone
        }

        constrain(controlBtn) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)

            horizontalChainWeight = 0.5f
            verticalChainWeight = 0.5f

            width = Dimension.value(150.dp)
            height = Dimension.value(150.dp)
        }
    }

    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier
    ) {
        AndroidView(
            factory = { context ->
                YouTubePlayerView(context).apply {
                    enableBackgroundPlayback(true)

                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)

                            ytPlayer = youTubePlayer

                            ytPlayer?.cueVideo(
                                videoId = youTubeVideoId,
                                startSeconds = 0f
                            )

                            // loadVideo() plays the video automatically after loading
                            // ytPlayer?.loadVideo(
                            //     videoId = youTubeVideoId,
                            //     startSeconds = 0f
                            // )
                        }

                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerConstants.PlayerState
                        ) {
                            super.onStateChange(youTubePlayer, state)
                            Timber.i(
                                """
                                onStateChange:
                                state = $state
                            """.trimIndent()
                            )

                            videoPlayingState = when (state) {
                                PlayerConstants.PlayerState.VIDEO_CUED -> videoPlayingState.copy(
                                    isPlaying = false,
                                    isBuffering = false,
                                    isVideoCued = true
                                )
                                PlayerConstants.PlayerState.UNSTARTED -> videoPlayingState.copy(
                                    isPlaying = false,
                                    isBuffering = true
                                )
                                PlayerConstants.PlayerState.BUFFERING -> videoPlayingState.copy(
                                    isPlaying = true,
                                    isBuffering = true
                                )
                                PlayerConstants.PlayerState.PLAYING -> videoPlayingState.copy(
                                    isPlaying = true,
                                    isBuffering = false
                                )
                                PlayerConstants.PlayerState.PAUSED, PlayerConstants.PlayerState.ENDED, PlayerConstants.PlayerState.UNKNOWN -> videoPlayingState.copy(
                                    isPlaying = false,
                                    isBuffering = false
                                )
                            }
                        }

                        override fun onError(
                            youTubePlayer: YouTubePlayer,
                            error: PlayerConstants.PlayerError
                        ) {
                            super.onError(youTubePlayer, error)
                            Timber.e("onError = $error")
                        }
                    })
                }
            },
            update = { player ->
                Timber.i("inside update")
            },
            onRelease = { player ->
                // Called when the screen is closed and is not active anymore
                Timber.i("inside onRelease")
            },
            modifier = Modifier.layoutId("youTubePlayer")
        )

        Button(
            enabled = videoPlayingState.isVideoCued && !videoPlayingState.isBuffering,
            onClick = {
                scope.launch {
                    when {
                        videoPlayingState.isPlaying -> {
                            Intent(context, YoutubePlayerService::class.java).also {
                                it.action = YoutubePlayerService.Actions.STOP.toString()
                                context.startService(it)
                            }
                            ytPlayer?.pause()
                        }
                        !videoPlayingState.isPlaying -> {
                            Intent(context, YoutubePlayerService::class.java).also {
                                it.action = YoutubePlayerService.Actions.START.toString()
                                context.startService(it)
                            }
                            ytPlayer?.play()
                        }
                    }
                }
            },
            modifier = Modifier.layoutId("controlBtn")
        ) {
            if (!videoPlayingState.isVideoCued) {
                Text(text = "Preparing...")
            } else if (videoPlayingState.isBuffering) {
                CircularProgressIndicator(
                    color = Color.Yellow
                )
            } else {
                when {
                    videoPlayingState.isPlaying -> Text(text = "Pause")
                    !videoPlayingState.isPlaying -> Text(text = "Play")
                }
            }
        }
    }
}