package com.xeniac.youtubemusicplayer.feature_youtube_player.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.components.ViewYoutubePlayer

@Composable
fun YouTubePlayerScreen() {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPaddings ->
        ViewYoutubePlayer(
            youTubeVideoId = "jfKfPfyJRdk",
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp + innerPaddings.calculateTopPadding(),
                    bottom = 16.dp + innerPaddings.calculateBottomPadding()
                )
        )
    }
}