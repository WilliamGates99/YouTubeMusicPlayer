package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation

import androidx.lifecycle.ViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.states.YouTubePlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class YouTubePlayerViewModel @Inject constructor() : ViewModel() {

    private val _youTubePlayerState = MutableStateFlow(YouTubePlayerState())
    val youTubePlayerState = _youTubePlayerState.asStateFlow()

    init {
        updateYouTubeVideoId(videoId = "jfKfPfyJRdk")
    }

    fun updateYouTubeVideoId(videoId: String) {
        _youTubePlayerState.update {
            it.copy(youTubeVideoId = videoId)
        }
    }

    fun initializeYouTubePlayer(player: YouTubePlayer) {
        _youTubePlayerState.update {
            it.copy(youTubePlayer = player)
        }

        youTubePlayerState.value.apply {
            youTubeVideoId?.let { videoId ->
                youTubePlayer?.cueVideo(
                    videoId = videoId,
                    startSeconds = 0f
                )
            }
        }
    }

    fun updatePlayerStateToCued() {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = false,
                isVideoCued = true
            )
        }
    }

    fun updatePlayerStateToUnstarted() {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = true
            )
        }
    }

    fun updatePlayerStateToBuffering() {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = true,
                isBuffering = true
            )
        }
    }

    fun updatePlayerStateToPlaying() {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = true,
                isBuffering = false
            )
        }
    }

    fun updatePlayerStateToPaused() {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = false
            )
        }
    }

    fun updatePlayerStateToEnded() {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = false
            )
        }
    }

    fun updatePlayerStateToUnknown() {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = false
            )
        }
    }

    fun showErrorMessage(message: String) {
        _youTubePlayerState.update {
            it.copy(errorMessage = message)
        }
    }
}