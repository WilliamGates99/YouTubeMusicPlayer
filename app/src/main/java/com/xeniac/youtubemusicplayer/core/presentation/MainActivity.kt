package com.xeniac.youtubemusicplayer.core.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.xeniac.youtubemusicplayer.core.presentation.components.ViewYoutubePlayer
import com.xeniac.youtubemusicplayer.core.ui.theme.TempYoutubePlayerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                /* activity = */ this,
                /* permissions = */ arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                /* requestCode = */ 0
            )
        }

        setContent {
            TempYoutubePlayerTheme {
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

                    // KmpYoutubePlayer(
                    //     youTubeVideoId = "jfKfPfyJRdk",
                    //     modifier = Modifier
                    //         .fillMaxSize()
                    //         .padding(
                    //             start = 16.dp,
                    //             end = 16.dp,
                    //             top = 16.dp + innerPaddings.calculateTopPadding(),
                    //             bottom = 16.dp + innerPaddings.calculateBottomPadding()
                    //         )
                    // )
                }
            }
        }
    }
}