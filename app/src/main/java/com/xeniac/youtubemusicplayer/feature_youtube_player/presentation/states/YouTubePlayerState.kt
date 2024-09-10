package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.states

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

data class YouTubePlayerState(
    val youTubePlayer: YouTubePlayer? = null,
    val youTubeVideoId: String? = null,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val isVideoCued: Boolean = false,
    val errorMessage: String? = null
)