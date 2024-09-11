package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.states.YouTubePlayerState
import com.xeniac.youtubemusicplayer.feature_youtube_player.services.YouTubePlayerService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class YouTubePlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _youTubePlayerState = MutableStateFlow(YouTubePlayerState())
    val youTubePlayerState = _youTubePlayerState.asStateFlow()

    init {
        updateYouTubeVideoId(
            videoId = "jfKfPfyJRdk",
            videoTitle = "lofi hip hop radio \uD83D\uDCDA beats to relax/study to",
            channelName = "Lofi Girl"
        )

        Timber.i("init") // TODO: TEMP
    }

    // TODO: TEMP
    override fun onCleared() {
        super.onCleared()
        Timber.e("onCleared")
    }

    fun onAction(action: YouTubePlayerAction) {
        when (action) {
            is YouTubePlayerAction.UpdateYouTubeVideoId -> updateYouTubeVideoId(
                videoId = action.videoId,
                videoTitle = action.videoTitle,
                channelName = action.channelName
            )
            is YouTubePlayerAction.InitializeYouTubePlayer -> initializeYouTubePlayer(action.player)
            YouTubePlayerAction.PlayVideo -> playVideo()
            YouTubePlayerAction.PauseVideo -> pauseVideo()
            is YouTubePlayerAction.ShowErrorMessage -> showErrorMessage(action.message)
            YouTubePlayerAction.UpdatePlayerStateToCued -> updatePlayerStateToCued()
            YouTubePlayerAction.UpdatePlayerStateToUnstarted -> updatePlayerStateToUnstarted()
            YouTubePlayerAction.UpdatePlayerStateToBuffering -> updatePlayerStateToBuffering()
            YouTubePlayerAction.UpdatePlayerStateToPlaying -> updatePlayerStateToPlaying()
            YouTubePlayerAction.UpdatePlayerStateToPaused -> updatePlayerStateToPaused()
            YouTubePlayerAction.UpdatePlayerStateToEnded -> updatePlayerStateToEnded()
            YouTubePlayerAction.UpdatePlayerStateToUnknown -> updatePlayerStateToUnknown()
        }
    }

    private fun updateYouTubeVideoId(
        videoId: String,
        videoTitle: String,
        channelName: String
    ) = viewModelScope.launch {
        _youTubePlayerState.update {
            it.copy(
                youtubeVideoId = videoId,
                videoTitle = videoTitle,
                channelName = channelName
            )
        }
    }

    private fun initializeYouTubePlayer(player: YouTubePlayer) = viewModelScope.launch {
        _youTubePlayerState.update {
            it.copy(youtubePlayer = player)
        }

        youTubePlayerState.value.apply {
            youtubeVideoId?.let { videoId ->
                youtubePlayer?.cueVideo(
                    videoId = videoId,
                    startSeconds = 0f
                )
            }
        }
    }

    private fun playVideo() = viewModelScope.launch {
        startYouTubePlayerService()
        youTubePlayerState.value.youtubePlayer?.play()
    }

    private fun pauseVideo() = viewModelScope.launch {
        stopYouTubePlayerService()
        youTubePlayerState.value.youtubePlayer?.pause()
    }

    private fun showErrorMessage(message: String) = viewModelScope.launch {
        stopYouTubePlayerService()
        _youTubePlayerState.update {
            it.copy(
                errorMessage = message,
                isPlaying = false,
                isBuffering = false
            )
        }
    }

    private fun updatePlayerStateToCued() = viewModelScope.launch {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = false,
                isVideoCued = true
            )
        }
    }

    private fun updatePlayerStateToUnstarted() = viewModelScope.launch {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = true
            )
        }
    }

    private fun updatePlayerStateToBuffering() = viewModelScope.launch {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = true,
                isBuffering = true,
                errorMessage = null
            )
        }
    }

    private fun updatePlayerStateToPlaying() = viewModelScope.launch {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = true,
                isBuffering = false,
                errorMessage = null
            )
        }
    }

    private fun updatePlayerStateToPaused() = viewModelScope.launch {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = false
            )
        }
    }

    private fun updatePlayerStateToEnded() = viewModelScope.launch {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = false
            )
        }
    }

    private fun updatePlayerStateToUnknown() = viewModelScope.launch {
        _youTubePlayerState.update {
            it.copy(
                isPlaying = false,
                isBuffering = false
            )
        }
    }

    private fun startYouTubePlayerService() {
        Intent(context, YouTubePlayerService::class.java).also {
            it.action = YouTubePlayerService.Actions.START_SERVICE.toString()

            it.putExtra(
                /* name = */ "channelName",
                /* value = */ youTubePlayerState.value.channelName
            )
            it.putExtra(
                /* name = */ "videoTitle",
                /* value = */ youTubePlayerState.value.videoTitle
            )

            context.startService(it)
        }
    }

    private fun stopYouTubePlayerService() {
        Intent(context, YouTubePlayerService::class.java).also {
            it.action = YouTubePlayerService.Actions.STOP_SERVICE.toString()
            context.startService(it)
        }
    }
}