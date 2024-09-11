package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

sealed interface YouTubePlayerAction {
    data class UpdateYouTubeVideoId(
        val videoId: String,
        val videoTitle: String,
        val channelName: String
    ) : YouTubePlayerAction

    data class InitializeYouTubePlayer(val player: YouTubePlayer) : YouTubePlayerAction

    data object PlayVideo : YouTubePlayerAction
    data object PauseVideo : YouTubePlayerAction
    data class ShowErrorMessage(val message: String) : YouTubePlayerAction

    data object UpdatePlayerStateToCued : YouTubePlayerAction
    data object UpdatePlayerStateToUnstarted : YouTubePlayerAction
    data object UpdatePlayerStateToBuffering : YouTubePlayerAction
    data object UpdatePlayerStateToPlaying : YouTubePlayerAction
    data object UpdatePlayerStateToPaused : YouTubePlayerAction
    data object UpdatePlayerStateToEnded : YouTubePlayerAction
    data object UpdatePlayerStateToUnknown : YouTubePlayerAction
}