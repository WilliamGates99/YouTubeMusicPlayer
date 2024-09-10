package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.states

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

data class YouTubePlayerState(
    val youtubePlayer: YouTubePlayer? = null,
    val youtubeVideoId: String? = null,
    val videoTitle: String? = null,
    val channelName: String? = null,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val isVideoCued: Boolean = false,
    val errorMessage: String? = null
)