package com.xeniac.youtubemusicplayer.core.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.xeniac.youtubemusicplayer.core.ui.navigation.nav_graph.SetupRootNavGraph
import com.xeniac.youtubemusicplayer.core.ui.theme.YouTubeMusicPlayerTheme
import com.xeniac.youtubemusicplayer.core.ui.theme.utils.enableEdgeToEdgeWindow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdgeWindow()
        installSplashScreen()
        requestPostNotificationPermission()

        setContent {
            YouTubeMusicPlayerRootSurface()
        }
    }

    private fun requestPostNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                /* activity = */ this,
                /* permissions = */ arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                /* requestCode = */ 0
            )
        }
    }

    @Composable
    private fun YouTubeMusicPlayerRootSurface() {
        YouTubeMusicPlayerTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val rootNavController = rememberNavController()

                SetupRootNavGraph(
                    rootNavController = rootNavController
                )
            }
        }
    }
}